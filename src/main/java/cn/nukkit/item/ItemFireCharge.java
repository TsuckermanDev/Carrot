package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFire;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;

import java.util.Random;

public class ItemFireCharge extends Item {

    public ItemFireCharge() {
        this(0, 1);
    }

    public ItemFireCharge(Integer meta) {
        this(meta, 1);
    }

    public ItemFireCharge(Integer meta, int count) {
        super(FIRE_CHARGE, meta, count, "Fire Charge");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (target.getId() == Block.AIR && (target instanceof BlockSolid)) {
            level.setBlock(block, new BlockFire());

            block = level.getBlock(block);
            if (block.getSide(BlockFace.DOWN).isTopFacingSurfaceSolid() || block.canNeighborBurn()) {
                Random random = new Random();
                level.scheduleUpdate(block, block.tickRate() + random.nextInt(10 + 1)); //nextInt top
            }

            if (player.isSurvival()) {
                this.useOn(block);
                player.getInventory().setItemInHand(this);
            }
            return true;
        }
        return false;
    }

}
