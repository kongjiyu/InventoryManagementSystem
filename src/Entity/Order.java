package Entity;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
    private int OrderID;
    private HashMap<Product, Integer> cart;
    private double discount;
    private double price;
    private LocalDateTime OrderDate;
    private String StaffID;
    private String PaymentID;

    public Order(){

    }

    public Order(int orderID){
        this.OrderID = orderID;
        this.cart = new HashMap<Product, Integer>();
        this.discount = 0;
        this.price = 0;
        this.OrderDate = LocalDateTime.now();
        this.StaffID = "";
        this.PaymentID = "";
    }

    public Order(int orderID, HashMap<Product, Integer> cart, double discount, double price, LocalDateTime orderDate, String staffID, String paymentID) {
        OrderID = orderID;
        this.cart = cart;
        this.discount = discount;
        this.price = price;
        OrderDate = orderDate;
        StaffID = staffID;
        PaymentID = paymentID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public void setCart(Product product, int quantity){
        cart.put(product, quantity);
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
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
