package Entity;

import java.time.LocalDateTime;

public class Inventory {
    private String id;
    private Product product;
    private int quantity;
    private Supplier supplier;
    private String inventoryType;
    private LocalDateTime time;

    public Inventory(){

    }

    public Inventory(String id, Product product, int quantity, Supplier supplier, String inventoryType, LocalDateTime time) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.supplier = supplier;
        this.inventoryType = inventoryType;
        this.time = time;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
