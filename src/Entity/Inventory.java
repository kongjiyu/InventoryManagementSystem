package Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Inventory {
    private String id;
    private int productUPC;
    private int quantity;
    private double price;
    private String supplierID;
    private String retailerID;
    private String warehouseID;
    private String inventoryType;
    private LocalDateTime inventoryTime;
    private String remarks;
    private String receivedBy;

    public Inventory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductUPC() {
        return productUPC;
    }

    public void setProductUPC(int product) {
        this.productUPC = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplier) {
        this.supplierID = supplier;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public LocalDateTime getInventoryTime() {
        return inventoryTime;
    }

    public void setInventoryTime(LocalDateTime inventoryTime) {
        this.inventoryTime = inventoryTime;
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

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }
}
