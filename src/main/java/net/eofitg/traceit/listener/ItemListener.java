package net.eofitg.traceit.listener;

import net.eofitg.traceit.TraceIt;
import net.eofitg.traceit.util.BWUtil;
import net.eofitg.traceit.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ItemListener {

    @SubscribeEvent
    public void OnPickupItem(PlayerEvent.ItemPickupEvent event) {
        if (TraceIt.config.isOnlyInBw() && BWUtil.notIBw()) return;
        ItemStack itemStack = event.pickedUp.getEntityItem();
        Item item = itemStack.getItem();
        EntityPlayer player = event.player;

        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock) item).block;
            if (TraceIt.config.blockContains(block)) {
                PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + block.getLocalizedName() + EnumChatFormatting.RESET + " was picked up by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
            }
        }

        else {
            if (TraceIt.config.itemContains(itemStack)) {
                PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + itemStack.getDisplayName() + EnumChatFormatting.RESET + " was picked up by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
            }
        }
    }

    @SubscribeEvent
    public void OnEntityJoinWorld(EntityJoinWorldEvent event) {
        if (TraceIt.config.isOnlyInBw() && BWUtil.notIBw()) return;
        if (!(event.entity instanceof EntityItem)) return;
        EntityItem entityItem = (EntityItem) event.entity;
        if (entityItem.getThrower() == null) return;


        ItemStack itemStack = entityItem.getEntityItem();
        Item item = itemStack.getItem();

        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock) item).block;
            if (TraceIt.config.blockContains(block)) {
                EntityPlayer player = PlayerUtil.getPlayerByName(entityItem.getThrower());
                if (player != null) {
                    PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + block.getLocalizedName() + EnumChatFormatting.RESET + " was dropped by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
                }
            }
        }

        else {
            if (TraceIt.config.itemContains(itemStack)) {
                EntityPlayer player = PlayerUtil.getPlayerByName(entityItem.getThrower());
                if (player != null) {
                    PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + itemStack.getDisplayName() + EnumChatFormatting.RESET + " was dropped by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
                }
            }
        }
    }

}
