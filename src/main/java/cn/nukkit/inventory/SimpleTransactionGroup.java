package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class SimpleTransactionGroup implements TransactionGroup {

    protected Player player;

    protected Transaction[] transactionQueue = new Transaction[]{};
    protected Transaction[] transactionsToRetry = new Transaction[]{};

    protected final Set<Inventory> inventories = new HashSet<>();

    protected float lastUpdate = -1;

    protected int transactionCount = 0;

    public SimpleTransactionGroup(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Set<Inventory> getInventories() {
        return inventories;
    }

    @Override
    public Transaction[] getTransactions() {
        return transactionQueue;
    }

    @Override
    public int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactionQueue = ArrayUtils.add(this.transactionQueue, transaction);
        if (transaction.getInventory() != null) {
            this.inventories.add(transaction.getInventory());
        }

        this.lastUpdate = System.currentTimeMillis();
        this.transactionCount += 1;
    }

    @Override
    public boolean execute() {
        Set<Transaction> failed = new HashSet<>();

        if (!ArrayUtils.isEmpty(this.transactionsToRetry)) {
            ArrayUtils.reverse(this.transactionsToRetry);
            this.transactionQueue = ArrayUtils.addAll(this.transactionQueue, this.transactionsToRetry);
            this.transactionsToRetry = new Transaction[]{};
        }

        InventoryTransactionEvent transactionEvent = new InventoryTransactionEvent(this);
        if (!ArrayUtils.isEmpty(this.transactionQueue)) {
            this.player.getServer().getPluginManager().callEvent(transactionEvent);
        } else {
            return false;
        }

        if (!ArrayUtils.isEmpty(this.transactionQueue)) {
            ArrayUtils.reverse(this.transactionQueue);
            for (Transaction transaction : this.transactionQueue) {
                if (transaction.getInventory() instanceof ContainerInventory || transaction.getInventory() instanceof PlayerInventory) {
                    InventoryClickEvent clickEvent = new InventoryClickEvent(transaction.getInventory(), this.player, transaction.getSlot(), transaction.getInventory().getItem(transaction.getSlot()));
                    this.player.getServer().getPluginManager().callEvent(clickEvent);

                    if (clickEvent.isCancelled()) {
                        transactionEvent.setCancelled(true);
                    }
                }

                if (transactionEvent.isCancelled()) {
                    this.transactionCount -= 1;
                    transaction.sendSlotUpdate(this.player);
                    this.inventories.remove(transaction.getInventory());
                    continue;
                } else if (!transaction.execute(this.player)) {
                    transaction.addFailure();
                    if (transaction.getFailures() >= DEFAULT_ALLOWED_RETRIES) {
                        this.transactionCount -= 1;
                        failed.add(transaction);
                    } else {
                        this.transactionsToRetry = ArrayUtils.add(this.transactionsToRetry, transaction);
                    }
                    continue;
                }

                this.transactionCount -= 1;
                transaction.setSuccess();
                transaction.sendSlotUpdate(this.player);
                this.inventories.remove(transaction.getInventory());
            }
            this.transactionQueue = new Transaction[]{};
        }

        for (Transaction fail : failed) {
            fail.sendSlotUpdate(this.player);
            this.inventories.remove(fail.getInventory());
        }

        return true;
    }
}
