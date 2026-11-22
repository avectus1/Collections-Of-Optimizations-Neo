package com.misanthropy.collections_of_optimizations.core;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL30C;

public final class RenderTargetLeaks {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static int freed;

   private RenderTargetLeaks() {
   }

   public static void report(String owner, int width, int height, int framebuffer, int color, int depth) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft != null) {
         minecraft.execute(() -> free(minecraft, owner, width, height, framebuffer, color, depth));
      }
   }

   private static void free(Minecraft minecraft, String owner, int width, int height, int framebuffer, int color, int depth) {
      RenderTarget main = minecraft.getMainRenderTarget();
      if (framebuffer != main.frameBufferId && GL30C.glIsFramebuffer(framebuffer)) {
         int boundRead = GL11C.glGetInteger(36010);
         GlStateManager._glBindFramebuffer(36008, framebuffer);
         int colorType = attachedAs(36064, color);
         int depthType = attachedAs(36096, depth);
         GlStateManager._glBindFramebuffer(36008, boundRead);
         if (colorType == 5890 && color != main.getColorTextureId()) {
            TextureUtil.releaseTextureId(color);
         }

         if (depthType == 5890 && depth != main.getDepthTextureId()) {
            TextureUtil.releaseTextureId(depth);
         } else if (depthType == 36161) {
            GL30C.glDeleteRenderbuffers(depth);
         }

         GlStateManager._glDeleteFramebuffers(framebuffer);
         int count = ++freed;
         if (count <= 8 || Integer.bitCount(count) == 1) {
            LOGGER.warn("Freed leaked render target #{}: {} {}x{}, dropped without destroyBuffers()", count, owner, width, height);
         }
      }
   }

   private static int attachedAs(int point, int name) {
      if (name < 0) {
         return 0;
      } else {
         int type = GL30C.glGetFramebufferAttachmentParameteri(36008, point, 36048);
         if (type == 0) {
            return 0;
         } else {
            return GL30C.glGetFramebufferAttachmentParameteri(36008, point, 36049) == name ? type : 0;
         }
      }
   }
}
