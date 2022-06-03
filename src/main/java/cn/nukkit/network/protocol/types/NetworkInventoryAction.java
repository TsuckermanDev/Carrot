package cn.nukkit.network.protocol.types;

import cn.nukkit.item.Item;

public class NetworkInventoryAction {

    public static final int SOURCE_CONTAINER = 0;

    public static final int SOURCE_WORLD = 2; //drop/pickup item entity
    public static final int SOURCE_CREATIVE = 3;
    public static final int SOURCE_TODO = 99999;

    /**
     * Fake window IDs for the SOURCE_TODO type (99999)
     * <p>
     * These identifiers are used for inventory source types which are not currently implemented server-side in MCPE.
     * As a general rule of thumb, anything that doesn't have a permanent inventory is client-side. These types are
     * to allow servers to track what is going on in client-side windows.
     * <p>
     * Expect these to change in the future.
     */
    public static final int SOURCE_TYPE_CRAFTING_ADD_INGREDIENT = -2;
    public static final int SOURCE_TYPE_CRAFTING_REMOVE_INGREDIENT = -3;
    public static final int SOURCE_TYPE_CRAFTING_RESULT = -4;
    public static final int SOURCE_TYPE_CRAFTING_USE_INGREDIENT = -5;

    public static final int SOURCE_TYPE_ANVIL_INPUT = -10;
    public static final int SOURCE_TYPE_ANVIL_MATERIAL = -11;
    public static final int SOURCE_TYPE_ANVIL_RESULT = -12;
    public static final int SOURCE_TYPE_ANVIL_OUTPUT = -13;

    public static final int SOURCE_TYPE_ENCHANT_INPUT = -15;
    public static final int SOURCE_TYPE_ENCHANT_MATERIAL = -16;
    public static final int SOURCE_TYPE_ENCHANT_OUTPUT = -17;

    public static final int SOURCE_TYPE_TRADING_INPUT_1 = -20;
    public static final int SOURCE_TYPE_TRADING_INPUT_2 = -21;
    public static final int SOURCE_TYPE_TRADING_USE_INPUTS = -22;
    public static final int SOURCE_TYPE_TRADING_OUTPUT = -23;

    public static final int SOURCE_TYPE_BEACON = -24;

    /**
     * Any client-side window dropping its contents when the player closes it
     */
    public static final int SOURCE_TYPE_CONTAINER_DROP_CONTENTS = -100;

    public int sourceType;
    public int windowId;
    public long unknown;
    public int inventorySlot;
    public Item oldItem;
    public Item newItem;
}
