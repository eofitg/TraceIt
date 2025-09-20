package net.eofitg.traceit.config;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashSet;

public class TraceItConfig {

    private boolean enabled = true;
    private boolean onlyInBw = true;
    private final LinkedHashSet<String> objectList = new LinkedHashSet<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOnlyInBw() {
        return onlyInBw;
    }

    public void setOnlyInBw(boolean onlyInBw) {
        this.onlyInBw = onlyInBw;
    }

    public LinkedHashSet<String> getObjectList() {
        return objectList;
    }

    public boolean blockContains(Block block) {
        return objectList.contains(block.getRegistryName());
    }

    public void addBlock(Block block) {
        objectList.add(block.getRegistryName());
    }

    public boolean itemContains(ItemStack itemStack) {
        return objectList.contains(itemStack.getItem().getRegistryName());
    }

    public void addItem(ItemStack itemStack) {
        objectList.add(itemStack.getItem().getRegistryName());
    }

    public void removeObject(String obj) {
        objectList.remove(obj);
    }

}