package Entity;

import java.time.LocalDate;

public class Transfer {
    private String transferID;
    private String fromWarehouseID;
    private String toWarehouseID;
    private String productUPC;
    private int quantity;
    private LocalDate transferDate;

    public Transfer(){

    }

    public Transfer(String transferID, String fromWarehouseID, String toWarehouseID, String productUPC, int quantity) {
        this.transferID = transferID;
        this.fromWarehouseID = fromWarehouseID;
        this.toWarehouseID = toWarehouseID;
        this.productUPC = productUPC;
        this.quantity = quantity;
    }

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
    }

    public String getFromWarehouseID() {
        return fromWarehouseID;
    }

    public void setFromWarehouseID(String fromWarehouseID) {
        this.fromWarehouseID = fromWarehouseID;
    }

    public String getToWarehouseID() {
        return toWarehouseID;
    }

    public void setToWarehouseID(String toWarehouseID) {
        this.toWarehouseID = toWarehouseID;
    }

    public String getProductUPC() {
        return productUPC;
    }

    public void setProductUPC(String productUPC) {
        this.productUPC = productUPC;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }
}
