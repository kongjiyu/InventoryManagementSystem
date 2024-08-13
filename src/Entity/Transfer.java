package Entity;

import java.time.LocalDate;

public class Transfer {
    private String transferID;
    private Warehouse fromWarehouse;
    private Warehouse toWarehouse;
    private Product product;
    private int quantity;
    private LocalDate transferDate;

    public Transfer(){

    }

    public Transfer(String transferID, Warehouse fromWarehouse, Warehouse toWarehouse, Product product, int quantity) {
        this.transferID = transferID;
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.product = product;
        this.quantity = quantity;
    }

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
    }

    public Warehouse getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(Warehouse fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public Warehouse getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(Warehouse toWarehouse) {
        this.toWarehouse = toWarehouse;
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
}
