package net.eofitg.traceit.listener;

import net.eofitg.traceit.TraceIt;
import net.eofitg.traceit.util.BWUtil;
import net.eofitg.traceit.util.ChatBoard;
import net.eofitg.traceit.util.PlayerUtil;
import net.eofitg.traceit.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class GameOverlayListener {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderText(RenderGameOverlayEvent.Text event) {
        if (!TraceIt.config.isEnabled() || !TraceIt.config.isShowInBoard()) return;
        if (PlayerUtil.containsNull()) return;
        if (TraceIt.config.isOnlyInBw() && BWUtil.notIBw()) return;
        if (TraceIt.config.hideWhenGuiOpen && mc.currentScreen != null) return;


        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        setupMatrix(TraceIt.config.scale, TraceIt.config.xOffset, TraceIt.config.yOffset);

        TextUtil.drawVerticalTextByAlignAt(0, 0, new ArrayList<>(ChatBoard.board), TextUtil.getAlignById(TraceIt.config.align), TraceIt.config.spacing, TraceIt.config.shadow);

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private void setupMatrix(float scale, float xOffset, float yOffset) {
        ScaledResolution res = new ScaledResolution(mc);
        float x = res.getScaledWidth() / 2f;
        float y = res.getScaledHeight() / 2f;
        GlStateManager.translate(x + xOffset, y + yOffset, 0);  // offset
        GlStateManager.scale(scale, scale, 1);  // scale
    }

}
