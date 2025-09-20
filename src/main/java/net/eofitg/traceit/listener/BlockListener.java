package net.eofitg.traceit.listener;

import net.eofitg.traceit.TraceIt;
import net.eofitg.traceit.util.BWUtil;
import net.eofitg.traceit.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockListener {

    @SubscribeEvent
    public void OnPlaceBlock(BlockEvent.PlaceEvent event) {
        if (TraceIt.config.isOnlyInBw() && BWUtil.notIBw()) return;
        Block block = event.placedBlock.getBlock();
        if (!TraceIt.config.blockContains(block)) return;

        EntityPlayer player = event.player;
        PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + block.getLocalizedName() + EnumChatFormatting.RESET + " was placed by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
    }

    @SubscribeEvent
    public void OnBreakBlock(BlockEvent.BreakEvent event) {
        if (TraceIt.config.isOnlyInBw() && BWUtil.notIBw()) return;
        Block block = event.state.getBlock();
        if (!TraceIt.config.blockContains(block)) return;

        EntityPlayer player = event.getPlayer();
        PlayerUtil.addMessage(EnumChatFormatting.RESET + "" + EnumChatFormatting.UNDERLINE + block.getLocalizedName() + EnumChatFormatting.RESET + " was broke by " + EnumChatFormatting.BOLD + PlayerUtil.getColoredName(player));
    }

}
