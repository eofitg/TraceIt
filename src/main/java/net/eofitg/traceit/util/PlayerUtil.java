package net.eofitg.traceit.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class PlayerUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void addMessage(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[TraceIt] " + msg));
    }

    public static EntityPlayer getPlayerByName(String name) {
        return mc.theWorld.getPlayerEntityByName(name);
    }

    public static String getColoredName(EntityPlayer player) {
        ScorePlayerTeam team = (ScorePlayerTeam) player.getTeam();
        String color = "";
        if (team != null) {
            EnumChatFormatting format = team.getChatFormat();
            if (format != null && format.isColor()) {
                int index = format.getColorIndex(); // 0 - 15
                if (index >= 0 && index < 16) {
                    char code = "0123456789abcdef".charAt(index);
                    color = "§" + code;
                }
            }
            String teamPrefix = team.getColorPrefix();
            char code = getLastColorCharOf(teamPrefix);
            if (code != '\0') {
                color = "§" + code;
            }
        }
        return color + player.getName();
    }

    public static char getLastColorCharOf(String text) {
        if (text == null || text.isEmpty()) return '\0';
        for (int i = text.length() - 2; i >= 0; i--) {
            if (text.charAt(i) == '§') {
                char code = Character.toLowerCase(text.charAt(i + 1));
                if ((code >= '0' && code <= '9') || (code >= 'a' && code <= 'f')) {
                    return code;
                }
            }
        }
        return '\0';
    }

}
