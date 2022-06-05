package cn.nukkit.item;

import cn.nukkit.block.BlockShulkerBox;
import cn.nukkit.utils.DyeColor;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class ItemShulkerBox extends Item {

    public ItemShulkerBox() {
        this(0, 1);
    }

    public ItemShulkerBox(Integer meta) {
        this(meta, 1);
    }

    public ItemShulkerBox(Integer meta, int count) {
        super(SHULKER_BOX, meta, count, DyeColor.getByDyeData(meta).getName() + " Bed");
        this.block = new BlockShulkerBox();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
