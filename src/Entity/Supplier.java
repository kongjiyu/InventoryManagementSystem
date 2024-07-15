package Entity;

public class Supplier {
    private String SupplierID;
    private String SupplierName;

    public Supplier(String SupplierID, String SupplierName) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String SupplierID) {
        this.SupplierID = SupplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }
}
