package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.flywheel.FlwIndirectProgramsAccessor;
import com.misanthropy.collections_of_optimizations.mixin.flywheel.FlwInstancingProgramsAccessor;
import com.misanthropy.collections_of_optimizations.mixin.flywheel.FlwPipelineCompilerAccessor;
import dev.engine_room.flywheel.api.instance.InstanceType;
import dev.engine_room.flywheel.backend.compile.ContextShader;
import dev.engine_room.flywheel.backend.compile.IndirectPrograms;
import dev.engine_room.flywheel.backend.compile.InstancingPrograms;
import dev.engine_room.flywheel.backend.compile.PipelineCompiler;
import dev.engine_room.flywheel.backend.compile.PipelineCompiler.OitMode;
import dev.engine_room.flywheel.backend.compile.PipelineCompiler.PipelineProgramKey;
import dev.engine_room.flywheel.lib.material.SimpleLightShader;
import dev.engine_room.flywheel.lib.material.SimpleMaterialShaders;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.GameShuttingDownEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FlywheelWarm {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static final int MAX_LINES = 400;
   private static final int MAX_TRIES = 600;
   private static final Map<String, InstanceType<?>> TYPES = new ConcurrentHashMap<>();
   private static final Map<String, String> ORIGINS = new ConcurrentHashMap<>();
   private static final Set<String> KNOWN = ConcurrentHashMap.newKeySet();
   private static final ArrayDeque<String> PENDING = new ArrayDeque<>();
   private static boolean loaded;
   private static boolean dirty;
   private static int tries;
   private static int warmed;
   private static int utilStep;
   private static long warmNanos;

   private FlywheelWarm() {
   }

   public static void register() {
      NeoForge.EVENT_BUS.addListener(FlywheelWarm::onClientTick);
      NeoForge.EVENT_BUS.addListener(FlywheelWarm::onScreenOpening);
      NeoForge.EVENT_BUS.addListener(FlywheelWarm::onShutdown);
   }

   private static Path file() {
      return FMLPaths.CONFIGDIR.get().resolve("collections_of_optimizations").resolve("warm-flywheel.txt");
   }

   private static String typeKey(InstanceType<?> type) {
      return type.vertexShader() + " " + type.cullShader();
   }

   public static void registerType(InstanceType<?> type) {
      try {
         String key = typeKey(type);
         TYPES.putIfAbsent(key, type);
         StackWalker.getInstance()
            .walk(frames -> frames.filter(frame -> frame.getMethodName().equals("<clinit>")).findFirst())
            .ifPresent(frame -> ORIGINS.putIfAbsent(key, frame.getClassName()));
      } catch (Throwable var2) {
      }
   }

   private static void initOrigin(String origin) {
      try {
         Class.forName(origin, true, FlywheelWarm.class.getClassLoader());
      } catch (Throwable var2) {
      }
   }

   public static void compiled(Object harness, Object key) {
      if (CoOConfig.flywheelWarmPrograms) {
         try {
            String line = describe(harness, key);
            if (line != null && KNOWN.size() < 400 && KNOWN.add(line)) {
               dirty = true;
            }
         } catch (Throwable var3) {
         }
      }
   }

   private static String role(Object harness) {
      IndirectPrograms indirect = IndirectPrograms.get();
      if (indirect != null) {
         FlwIndirectProgramsAccessor access = (FlwIndirectProgramsAccessor)indirect;
         if (harness == ((FlwPipelineCompilerAccessor)(Object)access.coo$pipeline()).coo$harness()) {
            return "indirect";
         }

         if (harness == access.coo$culling()) {
            return "cull";
         }
      }

      InstancingPrograms instancing = InstancingPrograms.get();
      if (instancing != null) {
         PipelineCompiler pipeline = ((FlwInstancingProgramsAccessor)instancing).coo$pipeline();
         if (harness == ((FlwPipelineCompilerAccessor)(Object)pipeline).coo$harness()) {
            return "instancing";
         }
      }

      return null;
   }

   private static String describe(Object harness, Object key) {
      String role = role(harness);
      if (role == null) {
         return null;
      } else {
         if (key instanceof PipelineProgramKey k && !role.equals("cull")) {
            if (!k.debugEnabled() && k.light() instanceof SimpleLightShader light && k.materialShaders() instanceof SimpleMaterialShaders shaders) {
               return role
                  + "|"
                  + typeKey(k.instanceType())
                  + "|"
                  + k.contextShader().name()
                  + "|"
                  + light.source()
                  + "|"
                  + shaders.vertexSource()
                  + "|"
                  + shaders.fragmentSource()
                  + "|"
                  + k.useCutout()
                  + "|"
                  + k.oit().name()
                  + "|"
                  + ORIGINS.getOrDefault(typeKey(k.instanceType()), "-");
            }

            return null;
         }

         if (key instanceof InstanceType<?> type && role.equals("cull")) {
            return role + "|" + typeKey(type) + "|" + ORIGINS.getOrDefault(typeKey(type), "-");
         }

         return null;
      }
   }

   private static boolean warm(String line) {
      String[] p = line.split("\\|");
      InstanceType<?> type = p.length > 1 ? TYPES.get(p[1]) : null;
      if (type == null && p.length > 2 && !p[p.length - 1].equals("-")) {
         initOrigin(p[p.length - 1]);
         type = TYPES.get(p[1]);
      }

      if (type == null) {
         return false;
      } else if (p[0].equals("cull") && p.length == 3) {
         IndirectPrograms indirect = IndirectPrograms.get();
         if (indirect == null) {
            return false;
         } else {
            ((FlwIndirectProgramsAccessor)indirect).coo$culling().get(type);
            return true;
         }
      } else if (p.length != 9) {
         return true;
      } else {
         PipelineCompiler pipeline = null;
         if (p[0].equals("indirect") && IndirectPrograms.get() != null) {
            pipeline = ((FlwIndirectProgramsAccessor)IndirectPrograms.get()).coo$pipeline();
         } else if (p[0].equals("instancing") && InstancingPrograms.get() != null) {
            pipeline = ((FlwInstancingProgramsAccessor)InstancingPrograms.get()).coo$pipeline();
         }

         if (pipeline == null) {
            return false;
         } else {
            PipelineProgramKey key = new PipelineProgramKey(
               type,
               ContextShader.valueOf(p[2]),
               new SimpleLightShader(ResourceLocation.parse(p[3])),
               new SimpleMaterialShaders(ResourceLocation.parse(p[4]), ResourceLocation.parse(p[5])),
               Boolean.parseBoolean(p[6]),
               false,
               OitMode.valueOf(p[7])
            );
            ((FlwPipelineCompilerAccessor)(Object)pipeline).coo$harness().get(key);
            return true;
         }
      }
   }

   private static void load() {
      loaded = true;

      try {
         Path path = file();
         if (Files.exists(path)) {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
               if (!line.isBlank() && KNOWN.add(line.trim())) {
                  PENDING.add(line.trim());
               }
            }
         }
      } catch (Throwable var3) {
         LOGGER.warn("Flywheel program warmup list could not be read", var3);
      }
   }

   private static void onClientTick(ClientTickEvent.Post event) {
      if (CoOConfig.flywheelWarmPrograms) {
         if (!loaded) {
            load();
         }

         if (PENDING.isEmpty()) {
            warmUtility();
         } else {
            String line = PENDING.peek();
            long begin = System.nanoTime();

            boolean done;
            try {
               done = warm(line);
            } catch (Throwable var6) {
               done = true;
               KNOWN.remove(line);
               dirty = true;
            }

            if (done) {
               PENDING.poll();
               warmed++;
               warmNanos = warmNanos + (System.nanoTime() - begin);
               if (PENDING.isEmpty()) {
                  LOGGER.info("Flywheel warmup compiled {} programs in {} ms", warmed, warmNanos / 1000000L);
               }
            } else if (++tries > 600) {
               LOGGER.info("Flywheel warmup gave up on {} programs it could not resolve", PENDING.size());
               PENDING.clear();
            }
         }
      }
   }

   private static void warmUtility() {
      if (utilStep < 6) {
         IndirectPrograms indirect = IndirectPrograms.get();
         if (indirect != null) {
            long begin = System.nanoTime();

            try {
               switch (utilStep) {
                  case 0:
                     indirect.getApplyProgram();
                     break;
                  case 1:
                     indirect.getScatterProgram();
                     break;
                  case 2:
                     indirect.getDownsampleFirstProgram();
                     break;
                  case 3:
                     indirect.getDownsampleSecondProgram();
                     break;
                  case 4:
                     indirect.oitPrograms().getOitCompositeProgram();
                     break;
                  default:
                     indirect.oitPrograms().getOitDepthProgram();
               }
            } catch (Throwable var4) {
               utilStep = 6;
               return;
            }

            utilStep++;
            warmNanos = warmNanos + (System.nanoTime() - begin);
            if (utilStep == 6) {
               LOGGER.info("Flywheel warmup compiled its 6 utility programs, {} ms in total", warmNanos / 1000000L);
            }
         }
      }
   }

   private static void onScreenOpening(Opening event) {
      if (event.getNewScreen() instanceof TitleScreen) {
         save();
      }
   }

   private static void onShutdown(GameShuttingDownEvent event) {
      save();
   }

   private static void save() {
      if (!loaded) {
         load();
      }

      if (dirty) {
         dirty = false;

         try {
            Path path = file();
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>(KNOWN);
            lines.sort(null);
            Path temp = path.resolveSibling("warm-flywheel.txt.tmp");
            Files.write(temp, lines, StandardCharsets.UTF_8);
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
         } catch (Throwable var3) {
            LOGGER.warn("Flywheel program warmup list could not be saved", var3);
         }
      }
   }
}
