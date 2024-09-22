package Model;

public class ProductRetailerInfo {
    private int productUPC;
    private int quantity;
    private String retailerID;
    private String retailerName;

    public ProductRetailerInfo(int productUPC, int quantity, String retailerID, String retailerName) {
        this.productUPC = productUPC;
        this.quantity = quantity;
        this.retailerID = retailerID;
        this.retailerName = retailerName;
    }

    // Getters and Setters
    public int getProductUPC() {
        return productUPC;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public String getRetailerName() {
        return retailerName;
    }
}