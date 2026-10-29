package net.satisfy.wildernature.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** Extract a preview before the screen restores the temporary entity pose. */
public final class EntityPreview {
    private EntityPreview() {}

    public static void extract(GuiGraphicsExtractor graphics, int x, int y, float scale,
            Vector3f translation, Quaternionf rotation, Quaternionf camera, LivingEntity entity) {
        var renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
        var state = renderer.createRenderState(entity, 1.0F);
        state.shadowPieces.clear();
        state.outlineColor = 0;
        state.nameTag = null;
        // The rectangle centre is the old preview origin; current scissor is preserved.
        int extent = Math.max(100, (int) Math.ceil(scale * (Math.max(entity.getBbWidth(), entity.getBbHeight()) + 1.0F)));
        graphics.entity(state, scale, translation, rotation, camera, x - extent, y - extent, x + extent, y + extent);
    }
}
