package com.misanthropy.collections_of_optimizations.core;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class XaeroColorCache {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("collections_of_optimizations").resolve("xaero_block_colors.txt");
   private static final Map<String, Integer> COLORS = new ConcurrentHashMap<>();
   private static volatile boolean loaded;
   private static String fingerprint;
   private static int unsaved;

   private XaeroColorCache() {
   }

   public static Integer get(ResourceLocation id, boolean convert) {
      load();
      return COLORS.get(key(id, convert));
   }

   public static void put(ResourceLocation id, boolean convert, int color) {
      load();
      if (COLORS.put(key(id, convert), color) == null && ++unsaved >= 32) {
         unsaved = 0;
         Util.ioPool().execute(XaeroColorCache::save);
      }
   }

   public static synchronized void reset() {
      if (loaded) {
         COLORS.clear();
         fingerprint = fingerprint();
         unsaved = 0;
      }
   }

   private static String key(ResourceLocation id, boolean convert) {
      return convert ? id + "|1" : id + "|0";
   }

   private static synchronized void load() {
      if (!loaded) {
         fingerprint = fingerprint();

         try {
            if (Files.exists(FILE)) {
               List<String> lines = Files.readAllLines(FILE, StandardCharsets.UTF_8);
               if (!lines.isEmpty() && lines.get(0).equals(fingerprint)) {
                  for (int i = 1; i < lines.size(); i++) {
                     String line = lines.get(i);
                     int split = line.lastIndexOf(61);
                     if (split > 0) {
                        COLORS.put(line.substring(0, split), Integer.parseInt(line.substring(split + 1)));
                     }
                  }
               }
            }
         } catch (Exception var4) {
            LOGGER.warn("Could not read {}: {}", FILE, var4.toString());
            COLORS.clear();
         }

         Runtime.getRuntime().addShutdownHook(new Thread(XaeroColorCache::save, "CoO Xaero colour cache save"));
         loaded = true;
      }
   }

   private static synchronized void save() {
      try {
         Files.createDirectories(FILE.getParent());
         Path temp = FILE.resolveSibling(FILE.getFileName() + ".tmp");

         try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            writer.write(fingerprint);
            writer.newLine();

            for (Entry<String, Integer> entry : new ArrayList<>(COLORS.entrySet())) {
               writer.write(entry.getKey());
               writer.write(61);
               writer.write(Integer.toString(entry.getValue()));
               writer.newLine();
            }
         }

         Files.move(temp, FILE, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception var6) {
         LOGGER.warn("Could not write {}: {}", FILE, var6.toString());
      }
   }

   private static String fingerprint() {
      StringBuilder builder = new StringBuilder();
      TreeSet<String> mods = new TreeSet<>();
      ModList.get().getMods().forEach(mod -> mods.add(mod.getModId() + "@" + mod.getVersion()));
      mods.forEach(mod -> builder.append(mod).append(';'));
      Path packs = FMLPaths.GAMEDIR.get().resolve("resourcepacks");

      for (String id : Minecraft.getInstance().getResourcePackRepository().getSelectedIds()) {
         builder.append(id);
         if (id.startsWith("file/")) {
            try {
               Path pack = packs.resolve(id.substring(5));
               builder.append('@').append(Files.size(pack)).append('@').append(Files.getLastModifiedTime(pack).toMillis());
            } catch (Exception var6) {
            }
         }

         builder.append(';');
      }

      return "v1 " + Integer.toHexString(builder.toString().hashCode()) + " " + builder.length();
   }
}
