package Entity;

public class Retailer {
    private String retailerId;
    private String retailerName;
    private String retailerAddress;
    private String retailerPhone;
    private String retailerEmail;

    public Retailer() {
    }

    public Retailer(String retailerId,String retailerName, String retailerAddress, String retailerPhone, String retailerEmail) {
        this.retailerId = retailerId;
        this.retailerName = retailerName;
        this.retailerAddress = retailerAddress;
        this.retailerPhone = retailerPhone;
        this.retailerEmail = retailerEmail;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getRetailerPhone() {
        return retailerPhone;
    }

    public void setRetailerPhone(String retailerPhone) {
        this.retailerPhone = retailerPhone;
    }

    public String getRetailerEmail() {
        return retailerEmail;
    }

    public void setRetailerEmail(String retailerEmail) {
        this.retailerEmail = retailerEmail;
    }
    
}
