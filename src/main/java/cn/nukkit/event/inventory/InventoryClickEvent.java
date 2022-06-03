package cn.nukkit.event.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

/**
 * author: boybook
 * Nukkit Project
 */
public class InventoryClickEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final int slot;
    private final Item item;
    private final Player who;

    public InventoryClickEvent(Inventory inventory, Player who, int slot, Item item) {
        super(inventory);
        this.slot = slot;
        this.who = who;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public Item getItem() {
        return item;
    }

    public Player getWhoClicked() {
        return who;
    }

    public Player getPlayer() {
        return who;
    }
}