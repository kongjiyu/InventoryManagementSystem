package Entity;

public class Storage {
    private String storageID;
    private Retailer retailer;
    private Product product;
    private Warehouse warehouse;
    private int quantity;

    public Storage(){

    }

    public Storage(Retailer retailer, Product product, int quantity) {
        this.retailer = retailer;
        this.product = product;
        this.quantity = quantity;
    }

    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
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
