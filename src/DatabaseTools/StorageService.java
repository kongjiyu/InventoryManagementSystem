package DatabaseTools;

import Entity.Product;

import java.util.List;

public interface StorageService {
    // get the Product List by insert Warehouse ID
    public List<Product> getProductListByWarehouseID(String warehouseID);

    // get product by Product Name
    public Product getProductByNameANDWarehouseID(String productName, String WarehouseID);

    // get product by Product UPC
    public Product getProductByUPCANDWarehouseID(int productUPC, String WarehouseID);

    //update the new quantity to the database by using productUPC
    public void updateProductQuantityByProductSKU(int productUPC, String warehouseID, int quantity);

    // create new product in the warehosue
    public void insertStorageForWarehouseProduct(String WarehouseID, int ProductUPC, int Quantity);

    // get the new storageID to create
    public String getNewStorageID();

    // get teh product refering the UPC and storage ID
    public Product getProductByUPCAndStorageID(int productUPC, String storageID, String storageType);

    // update the product quantity by refering UPC
    public void updateProductQuantityByProductUPC(int productUPC, String storageID, int quantity, String storageType);

    // insert new transfer into storage
    public void insertProductIntoStorage(String storageID, int productUPC, int quantity, String storageType);
}
