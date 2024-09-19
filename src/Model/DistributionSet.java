package Model;

public class DistributionSet {
    private String warehouseID;
    private String retailerID;
    private int productUPC;
    private int quantity;

    public DistributionSet(String warehouseID, String retailerID, int productUPC, int quantity) {
        this.warehouseID = warehouseID;
        this.retailerID = retailerID;
        this.productUPC = productUPC;
        this.quantity = quantity;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }

    public int getProductUPC() {
        return productUPC;
    }

    public void setProductUPC(int productUPC) {
        this.productUPC = productUPC;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
