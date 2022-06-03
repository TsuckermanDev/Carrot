package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.block.BlockIds;
import cn.nukkit.item.Item;

import java.util.Set;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BaseTransaction implements Transaction {

    protected final Inventory inventory;

    protected final int slot;

    protected Item targetItem;

    protected final long creationTime;

    protected final int transactionType;

    protected int failures = 0;

    protected boolean wasSuccessful = false;

    public BaseTransaction(Inventory inventory, int slot, Item targetItem, int transactionType) {
        this.inventory = inventory;
        this.slot = slot;
        this.targetItem = targetItem.clone();
        this.creationTime = System.currentTimeMillis();
        this.transactionType = transactionType;
    }

    public BaseTransaction(Inventory inventory, int slot, Item targetItem) {
        this(inventory, slot, targetItem, TYPE_NORMAL);
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public Item getTargetItem() {
        return targetItem.clone();
    }

    @Override
    public Item getSourceItem() {
        return inventory.getItem(slot).clone();
    }

    @Override
    public void setTargetItem(Item item) {
        this.targetItem = item.clone();
    }

    @Override
    public int getFailures() {
        return failures;
    }

    @Override
    public void addFailure(){
        this.failures++;
    }

    @Override
    public boolean succeeded() {
        return wasSuccessful;
    }

    @Override
    public void setSuccess(boolean value) {
        this.wasSuccessful = value;
    }

    @Override
    public void setSuccess() {
        this.wasSuccessful = true;
    }

    @Override
    public int getTransactionType() {
        return transactionType;
    }

    @Override
    public void sendSlotUpdate(Player source) {
        if (this.getInventory() instanceof TemporaryInventory) {
            return;
        }

        if (this.wasSuccessful) {
            Set<Player> targets = this.getInventory().getViewers();
            targets.remove(source);
            this.inventory.sendSlot(this.slot, targets);
        } else {
            this.inventory.sendSlot(this.slot, source);
        }
    }

    public InOfOutTransaction getChange() {
        Item sourceItem = this.getInventory().getItem(this.slot);

        if (sourceItem.equals(this.targetItem, true, true, true)) {
            return null;
        } else if (sourceItem.equals(this.targetItem)) {
            Item item = sourceItem.clone();
            int countDiff = this.targetItem.getCount() - sourceItem.getCount();
            item.setCount(countDiff < 0 ? countDiff * -1 : countDiff);

            if (countDiff < 0) {
                return new InOfOutTransaction(null, item);
            } else if (countDiff > 0) {
                return new InOfOutTransaction(item, null);
            } else {
                return null;
            }
        } else if (sourceItem.getId() != BlockIds.AIR && this.targetItem.getId() == BlockIds.AIR) {
            return new InOfOutTransaction(null, sourceItem.clone());
        } else if (sourceItem.getId() == BlockIds.AIR && this.targetItem.getId() != BlockIds.AIR) {
            return new InOfOutTransaction(this.getTargetItem(), null);
        } else {
            return new InOfOutTransaction(this.getTargetItem(), sourceItem.clone());
        }
    }

    @Override
    public boolean execute(Player source) {
        if (this.getInventory().processSlotChange(this)) {
            if (!source.getServer().allowInventoryCheats && !source.isCreative()) {
                InOfOutTransaction change = this.getChange();

                if (change == null) {
                    return true;
                }

                if (change.getOut() != null) {
                    if (!this.getInventory().slotContains(this.getSlot(), change.getOut())) {
                        return false;
                    }
                }

                if (change.getIn() != null) {
                    if (!source.getFloatingInventory().contains(change.getIn())) {
                        return false;
                    }
                }

                if (change.getOut() != null) {
                    source.getFloatingInventory().addItem(change.getOut());
                }

                if (change.getIn() != null) {
                    source.getFloatingInventory().removeItem(change.getIn());
                }
            }
            this.getInventory().setItem(this.getSlot(), this.getTargetItem());
        }
        return true;
    }

}
