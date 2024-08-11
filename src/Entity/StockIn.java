package Entity;

import java.time.LocalDate;

public class StockIn {
    private String id;
    private Product product;
    private int quantity;
    private LocalDate date;
    private LocalDate expiryDate;
    private String remarks;
    private String receivedBy;

    public StockIn(){

    }
    
    public StockIn(String id, Product product, int quantity, LocalDate date, LocalDate expiryDate, String remarks, String receivedBy) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.expiryDate = expiryDate;
        this.remarks = remarks;
        this.receivedBy = receivedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}
