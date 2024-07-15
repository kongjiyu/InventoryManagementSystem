package Entity;

public class Item {
    private String ItemSKU;
    private String ItemName;
    private double ItemPrice;
    private int ItemQuantity;

    public Item(String ItemSKU, String ItemName, double ItemPrice, int ItemQuantity) {
        this.ItemSKU = ItemSKU;
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
        this.ItemQuantity = ItemQuantity;
    }

    public String getItemSKU() {
        return ItemSKU;
    }

    public void setItemSKU(String ItemSKU) {
        this.ItemSKU = ItemSKU;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int ItemQuantity) {
        this.ItemQuantity = ItemQuantity;
    }
}
