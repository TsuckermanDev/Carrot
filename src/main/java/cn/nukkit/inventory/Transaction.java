package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public interface Transaction {

    int TYPE_NORMAL = 0;
    int TYPE_DROP_ITEM = 1;

    Inventory getInventory();

    void addFailure();

    int getFailures();

    boolean succeeded();

    void setSuccess();

    void setSuccess(boolean value);

    int getSlot();

    Item getTargetItem();

    void setTargetItem(Item item);

    Item getSourceItem();

    void sendSlotUpdate(Player source);

    int getTransactionType();

    long getCreationTime();

    boolean execute(Player source);
}
