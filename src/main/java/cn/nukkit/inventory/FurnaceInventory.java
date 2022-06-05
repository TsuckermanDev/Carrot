package cn.nukkit.inventory;

import cn.nukkit.blockentity.BlockEntityFurnace;
import cn.nukkit.item.Item;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class FurnaceInventory extends ContainerInventory {

    public final int SMELTING = 0;
    public final int FUEL = 1;
    public final int RESULT = 2;

    public FurnaceInventory(BlockEntityFurnace furnace) {
        super(furnace, InventoryType.FURNACE);
    }

    @Override
    public BlockEntityFurnace getHolder() {
        return (BlockEntityFurnace) this.holder;
    }

    public Item getResult() {
        return this.getItem(RESULT);
    }

    public Item getFuel() {
        return this.getItem(FUEL);
    }

    public Item getSmelting() {
        return this.getItem(SMELTING);
    }

    public boolean setResult(Item item) {
        return this.setItem(RESULT, item);
    }

    public boolean setFuel(Item item) {
        return this.setItem(FUEL, item);
    }

    public boolean setSmelting(Item item) {
        return this.setItem(SMELTING, item);
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        super.onSlotChange(index, before, send);

        this.getHolder().scheduleUpdate();
    }
}
