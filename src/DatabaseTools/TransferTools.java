package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;

public class TransferTools {

    // transfer stock from first warehouse to another warehouse with product id and quantity
    public boolean transferStock(String firstWarehouseID, String SecondWarehouseID, String ProductUPC, int quantity) {
        // create object
        StorageTools st = new StorageTools();
        ProductTools pt = new ProductTools();
        // check the product is in the warehouse or not
        if (checkProductInWarehouse(firstWarehouseID, ProductUPC)){
            Product productInFirstWarehouse = st.getProductByUPCANDWarehouseID(ProductUPC, firstWarehouseID);
            // change the first warehouse product quantity
            if (productInFirstWarehouse.getQuantity() >= quantity) {
                productInFirstWarehouse.setQuantity(productInFirstWarehouse.getQuantity() - quantity);
                st.updateProductQuantityByProductSKU(ProductUPC, firstWarehouseID, productInFirstWarehouse.getQuantity());
            }else {
                // if not enough stock
                System.out.printf("Not enough stock at Warehouse %s\n", firstWarehouseID);
                return false;
            }
            // check if there is product in the warehouse, then update quantity only.
            if(checkProductInWarehouse(SecondWarehouseID, ProductUPC)){
                Product productInSecondWarehouse = st.getProductByUPCANDWarehouseID(ProductUPC, SecondWarehouseID);
                st.updateProductQuantityByProductSKU(ProductUPC, SecondWarehouseID, productInSecondWarehouse.getQuantity()+ quantity);
            }else {
                // if don't have, need to insert new product inside
                Product newProduct = new Product(productInFirstWarehouse.getUPC(),
                        productInFirstWarehouse.getName(),
                        productInFirstWarehouse.getDesc(),
                        productInFirstWarehouse.getCategory(),
                        productInFirstWarehouse.getPrice(),
                        productInFirstWarehouse.getWeight(),
                        productInFirstWarehouse.getDimension(),
                        quantity,
                        LocalDateTime.now());
                ProductTools.insertProduct(newProduct);
            }
        }else {
            System.out.printf("Product doesn't exist in warehouse %s\n", firstWarehouseID);
            return false;
        }
        return true;
    }

    // check warehouse have certain product or not
    public boolean checkProductInWarehouse(String warehouseID, String productUPC) {
        Connection connection = DatabaseUtils.getConnection();

        String sql =    "SELECT * " +
                        "FROM product p " +
                        "JOIN warehouse w " +
                        "ON p.warehouseID = w.warehouseID " +
                        "WHERE warehouseID = ? " +
                        "AND productUPC = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, warehouseID);
            preparedStatement.setString(2, productUPC);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }else {
                return false;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
