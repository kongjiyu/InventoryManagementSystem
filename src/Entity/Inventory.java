package Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Inventory {
    private String id;
    private Product product;
    private int quantity;
    private Supplier supplier;
    private Retailer retailer;
    private String inventoryType;
    private LocalDateTime inventoryTime;
    private LocalDate expiryDate;
    private String remarks;
    private String receivedBy;

}
