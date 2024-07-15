package Entity;

import java.time.LocalDate;

public class Purchase {
    private String PurchaseID;
    private LocalDate PurchaseDate;
    private int ItemSKU;
    private int ItemQuantity;
    private double ItemPrice;
    private String SupplierID;

    public Purchase(String PurchaseID, LocalDate PurchaseDate, int ItemSKU, int ItemQuantity, double ItemPrice, String SupplierID) {
        this.PurchaseID = PurchaseID;
        this.PurchaseDate = PurchaseDate;
        this.ItemSKU = ItemSKU;
        this.ItemQuantity = ItemQuantity;
        this.ItemPrice = ItemPrice;
        this.SupplierID = SupplierID;
    }

    public String getPurchaseID() {
        return PurchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.PurchaseID = purchaseID;
    }

    public LocalDate getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.PurchaseDate = purchaseDate;
    }

    public int getItemSKU() {
        return ItemSKU;
    }

    public void setItemSKU(int itemSKU) {
        this.ItemSKU = itemSKU;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.ItemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.ItemPrice = itemPrice;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        this.SupplierID = supplierID;
    }

}
