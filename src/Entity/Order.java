package Entity;

import java.time.LocalDateTime;

public class Order {
    private int OrderID;
    private String ItemSKU;
    private int ItemQuantity;
    private LocalDateTime OrderDate;
    private String StaffID;
    private String PaymentID;

    public Order(int OrderID, String ItemSKU, int ItemQuantity, LocalDateTime OrderDate, String StaffID, String PaymentID) {
        this.OrderID = OrderID;
        this.ItemSKU = ItemSKU;
        this.ItemQuantity = ItemQuantity;
        this.OrderDate = OrderDate;
        this.StaffID = StaffID;
        this.PaymentID = PaymentID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getItemSKU() {
        return ItemSKU;
    }

    public void setItemSKU(String ItemSKU) {
        this.ItemSKU = ItemSKU;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int ItemQuantity) {
        this.ItemQuantity = ItemQuantity;
    }

    public LocalDateTime getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDateTime OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String StaffID) {
        this.StaffID = StaffID;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String PaymentID) {
        this.PaymentID = PaymentID;
    }
}
