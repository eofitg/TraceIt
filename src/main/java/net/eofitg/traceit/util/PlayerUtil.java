package net.eofitg.traceit.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;

import java.util.List;

public class PlayerUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean containsNull() {
        return mc == null || mc.thePlayer == null || mc.theWorld == null;
    }

    public static List<EntityPlayer> getWorldPlayerList() {
        return mc.theWorld.playerEntities;
    }

    public static NetworkPlayerInfo getPlayerInfo(EntityPlayer player) {
        for (NetworkPlayerInfo info : mc.thePlayer.sendQueue.getPlayerInfoMap()) {
            if (info.getGameProfile().getId().equals(player.getUniqueID())) {
                return info;
            }
        }
        return null;
    }

    public static boolean isFakePlayer(EntityPlayer player) {
        NetworkPlayerInfo playerInfo = getPlayerInfo(player);
        if (playerInfo == null)
            return true;

        if (playerInfo.getGameType() == WorldSettings.GameType.SPECTATOR)
            return true;

        return playerInfo.getResponseTime() <= 0;
    }

    public static void addMessage(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[TraceIt] " + msg));
    }

    public static EntityPlayer getThePlayer() {
        return mc.thePlayer;
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
