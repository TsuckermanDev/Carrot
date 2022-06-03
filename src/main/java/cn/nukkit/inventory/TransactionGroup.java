package cn.nukkit.inventory;

import java.util.Set;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public interface TransactionGroup {

    int DEFAULT_ALLOWED_RETRIES = 5;

    Set<Inventory> getInventories();

    Transaction[] getTransactions();

    int getTransactionCount();

    void addTransaction(Transaction transaction);

    boolean execute();

}
