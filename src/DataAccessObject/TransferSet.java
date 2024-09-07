package DataAccessObject;

public class TransferSet{
    private String firstWarehouseID;
    private String secondWarehouseID;
    private String ProductUPC;
    private int Quantity;

    TransferSet(String firstWarehouseID, String secondWarehouseID, String ProductUPC, int Quantity) {
        this.firstWarehouseID = firstWarehouseID;
        this.secondWarehouseID = secondWarehouseID;
        this.ProductUPC = ProductUPC;
        this.Quantity = Quantity;
    }

    public String getFirstWarehouseID() {
        return firstWarehouseID;
    }

    public void setFirstWarehouseID(String firstWarehouseID) {
        this.firstWarehouseID = firstWarehouseID;
    }

    public String getSecondWarehouseID() {
        return secondWarehouseID;
    }

    public void setSecondWarehouseID(String secondWarehouseID) {
        this.secondWarehouseID = secondWarehouseID;
    }

    public String getProductUPC() {
        return ProductUPC;
    }

    public void setProductUPC(String productUPC) {
        ProductUPC = productUPC;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
