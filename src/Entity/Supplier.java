package Entity;

public class Supplier {
    private String supplierId;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierEmail;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierAddress, String supplierPhone, String supplierEmail) {
        this.supplierId = supplierId;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
}
