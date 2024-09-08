package Entity;

import java.time.LocalDate;

public class StockRequest {
    private String requestId;
    private String itemId;
    private int quantity;
    private String requestBy;
    private String warehouseLocation;
    private LocalDate requestDate;

    public StockRequest(String requestId, String itemId, int quantity, String requestBy, String warehouseLocation, LocalDate requestDate) {
        this.requestId = requestId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.requestBy = requestBy;
        this.warehouseLocation = warehouseLocation;
        this.requestDate = requestDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}
