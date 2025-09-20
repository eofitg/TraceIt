package net.eofitg.traceit.command;

import net.eofitg.traceit.TraceIt;
import net.eofitg.traceit.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TraceItCommand extends CommandBase {

    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "toggle", "bw", "add", "remove"
    );

    @Override
    public String getCommandName() {
        return "traceit";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/traceit toggle|bw|add|remove <value>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) return;
        if (args.length == 0) {
            PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "toggle": {
                TraceIt.config.setEnabled(!TraceIt.config.isEnabled());
                boolean isEnabled = TraceIt.config.isEnabled();
                String status = isEnabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
                PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Mod " + status + EnumChatFormatting.GOLD + ".");
                break;
            }
            case "bw": {
                TraceIt.config.setOnlyInBw(!TraceIt.config.isOnlyInBw());
                boolean isEnabled = TraceIt.config.isOnlyInBw();
                String status = isEnabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
                PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Only activate in BW " + status + EnumChatFormatting.GOLD + ".");
                break;
            }
            case "add": {
                ItemStack itemStack = ((EntityPlayer) sender).getHeldItem();
                if (itemStack == null || itemStack.getItem() == null) {
                    PlayerUtil.addMessage(EnumChatFormatting.RED + "You are not holding anything.");
                    break;
                }

                Item heldItem = itemStack.getItem();
                if (heldItem instanceof ItemBlock) {
                    TraceIt.config.addBlock(((ItemBlock) heldItem).block);
                } else {
                    TraceIt.config.addItem(itemStack);
                }

                PlayerUtil.addMessage(EnumChatFormatting.GREEN + "New object added.");
                break;
            }
            case "remove": {
                if (args.length >= 2) {
                    TraceIt.config.removeObject(args[1]);
                    PlayerUtil.addMessage(EnumChatFormatting.GREEN + "Object removed.");
                } else {
                    PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Current object list: " + TraceIt.config.getObjectList().toString());
                }
                break;
            }
            default: {
                PlayerUtil.addMessage(EnumChatFormatting.RED + "Unknown argument: " + sub);
                PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            for (String cmd : SUBCOMMANDS) {
                if (cmd.startsWith(prefix)) {
                    completions.add(cmd);
                }
            }
        } else if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (sub.equals("remove")) {
                for (String obj : TraceIt.config.getObjectList()) {
                    if (obj.startsWith(args[1].toLowerCase())) {
                        completions.add(obj);
                    }
                }
            }
        }

        return completions.isEmpty() ? null : completions;
    }
}
