package net.eofitg.traceit.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;

import static net.eofitg.traceit.util.TextUtil.TextAlignEnum.*;

public class TextUtil {

    private static final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private static final int textColor = 0xFFFFFFFF;
    private static final int shadowColor = 0xAA000000;

    public static void drawVerticalTextByAlignAt(int x, int y, ArrayList<String> textArrayList, TextAlignEnum align, int spacing, boolean shadow) {
        if (textArrayList.isEmpty()) return;

        int ty = y;
        for (String text : textArrayList) {
            int tx = x;
            if (align != LEFT) {
                if (align == CENTER) {
                    tx -= fr.getStringWidth(text) / 2;
                } else if (align == RIGHT) {
                    tx -= fr.getStringWidth(text);
                }
            }
            if (shadow) fr.drawString(text, tx + 1, ty + 1, shadowColor, false);
            fr.drawString(text, tx, ty, textColor, false);
            ty += fr.FONT_HEIGHT + spacing;
        }
    }

    public enum TextAlignEnum {
        LEFT,
        RIGHT,
        CENTER
    }

    public static TextAlignEnum getAlignById(int id) {
        if (id == 0) return LEFT;
        if (id == 1) return RIGHT;
        if (id == 2) return CENTER;
        return LEFT;
    }

}
