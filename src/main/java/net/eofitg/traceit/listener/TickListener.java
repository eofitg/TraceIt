package net.eofitg.traceit.listener;

import net.eofitg.traceit.TraceIt;
import net.eofitg.traceit.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Objects;

public class TickListener {

    private static final HashMap<String, String> lastMessageForPlayerObject = new HashMap<>();

    @SubscribeEvent
    public void OnClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (PlayerUtil.containsNull()) return;

        for (EntityPlayer p : PlayerUtil.getWorldPlayerList()) {
            if (PlayerUtil.isFakePlayer(p)) continue;
            for (String objName : TraceIt.config.getObjectList()) {
                ItemStack itemStack = getItem(p, objName);
                int stackSize = 0;
                if (itemStack != null) stackSize = itemStack.stackSize;
                String mapKey = p.getName() + "|" + objName;
                String n = EnumChatFormatting.BOLD + "" + EnumChatFormatting.GREEN + stackSize + EnumChatFormatting.RESET;
                String dn = EnumChatFormatting.UNDERLINE + objName.substring("minecraft:".length());
                String msg = EnumChatFormatting.BOLD + PlayerUtil.getColoredName(p) + EnumChatFormatting.RESET + " has " + n + " " + dn;
                if (!lastMessageForPlayerObject.containsKey(mapKey) || !Objects.equals(lastMessageForPlayerObject.get(mapKey), msg)) {
                    PlayerUtil.addMessage(msg);
                    lastMessageForPlayerObject.put(mapKey, msg);
                }
            }
        }
    }

    @SubscribeEvent
    public void OnWorldUnLoad(WorldEvent.Unload event) {
        lastMessageForPlayerObject.clear();
    }

    private ItemStack getItem(EntityPlayer player, String regName) {
        for (ItemStack itemStack : player.inventory.armorInventory) {
            if (itemStack == null || itemStack.getItem() == null) continue;
            if (Objects.equals(itemStack.getItem().getRegistryName(), regName)) {
                return itemStack;
            }
        }
        for (ItemStack itemStack : player.inventory.mainInventory) {
            if (itemStack == null || itemStack.getItem() == null) continue;
            if (Objects.equals(itemStack.getItem().getRegistryName(), regName)) {
                return itemStack;
            }
        }
        return null;
    }

}
