package Entity;

import java.time.LocalDate;

public class StockRequest {
    private String requestId;
    private String productUPC;
    private int quantity;
    private String requestBy;
    private String warehouseId;
    private LocalDate requestDate;

    public StockRequest() {

    }

    public StockRequest(String requestId, String productUPC, int quantity, String requestBy, String warehouseId, LocalDate requestDate) {
        this.requestId = requestId;
        this.productUPC = productUPC;
        this.quantity = quantity;
        this.requestBy = requestBy;
        this.warehouseId = warehouseId;
        this.requestDate = requestDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

}