package Entity;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
    private int OrderID;
    private HashMap<String, Integer> OrderItems;
    private double discount;
    private double price;
    private LocalDateTime OrderDate;
    private String StaffID;
    private String PaymentID;


    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public void setOrderItems(String ItemSKU, int Quantity){
        OrderItems.put(ItemSKU, Quantity);
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
