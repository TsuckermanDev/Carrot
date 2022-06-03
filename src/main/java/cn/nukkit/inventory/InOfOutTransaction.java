package cn.nukkit.inventory;

import cn.nukkit.item.Item;

public class InOfOutTransaction {

    private Item in;
    private Item out;

    public InOfOutTransaction(Item in, Item out) {
        this.in = in;
        this.out = out;
    }

    public InOfOutTransaction() {
        this(null, null);
    }

    public void setIn(Item in) {
        this.in = in;
    }

    public void setOut(Item out) {
        this.out = out;
    }

    public Item getIn() {
        return in;
    }

    public Item getOut() {
        return out;
    }
}
