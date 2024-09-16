package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import Entity.Transfer;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;

public class TransferTools {

    // transfer stock from first warehouse to another warehouse with product id and quantity
    public boolean transferStock(String firstWarehouseID, String secondWarehouseID, int ProductUPC, int quantity) {
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
            if(checkProductInWarehouse(secondWarehouseID, ProductUPC)){
                Product productInSecondWarehouse = st.getProductByUPCANDWarehouseID(ProductUPC, secondWarehouseID);
                st.updateProductQuantityByProductSKU(ProductUPC, secondWarehouseID, productInSecondWarehouse.getQuantity()+ quantity);
            }else {
                // if don't have, need to insert new product inside
                st.insertStorageForWarehouseProduct(secondWarehouseID, ProductUPC, quantity);
            }
        }else {
            System.out.printf("Product doesn't exist in warehouse %s\n", firstWarehouseID);
            return false;
        }
        return true;
    }

    // check warehouse have certain product or not
    public boolean checkProductInWarehouse(String warehouseID, int productUPC) {
        Connection connection = DatabaseUtils.getConnection();

        String sql =    "SELECT * " +
                        "FROM product p " +
                        "JOIN storage s " +
                        "ON p.ProductUPC = s.ProductUPC " +
                        "WHERE s.WarehouseID = ? " +
                        "AND s.ProductUPC = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, warehouseID);
            preparedStatement.setInt(2, productUPC);
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

    // record transfer record
    public boolean recordTransfer(String TransferID, String FromWarehouse, String ToWarehosue, int ProductUPC, int Quantity){
        Connection connection = DatabaseUtils.getConnection();

        String sql = "INSERT INTO transfer VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, TransferID);
            preparedStatement.setString(2, FromWarehouse);
            preparedStatement.setString(3, ToWarehosue);
            preparedStatement.setInt(4, ProductUPC);
            preparedStatement.setInt(5, Quantity);
            preparedStatement.setDate(6, new java.sql.Date(Instant.now().toEpochMilli()));
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // get new transferID
    public String getNewTransferID(){
        // get connection to database
        Connection connection = DatabaseUtils.getConnection();
        String transferID = null;
        //sql string
        String sql =    "SELECT MAX(TransferID) " +
                        "AS MAX_TransferID " +
                        "FROM transfer";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return ("T" + String.format("%03d", (Integer.parseInt(resultSet.getString("MAX_TransferID").replace("T", "")) + 1)));
            }else {
                return "T001";
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean distributeStock(String warehouseID, String retailerID, int productUPC, int quantity) {
        StorageTools st = new StorageTools();

        // Check if the product exists in the warehouse
        if (st.checkProductInStorage(warehouseID, productUPC, "Warehouse")) {
            Product productInWarehouse = st.getProductByUPCAndStorageID(productUPC, warehouseID, "Warehouse");

            // Check if the warehouse has enough stock
            if (productInWarehouse.getQuantity() >= quantity) {
                // Decrease the quantity in the warehouse
                int newWarehouseQuantity = productInWarehouse.getQuantity() - quantity;
                st.updateProductQuantityByProductUPC(productUPC, warehouseID, newWarehouseQuantity, "Warehouse");
            } else {
                // Not enough stock
                System.out.printf("Not enough stock at Warehouse %s\n", warehouseID);
                return false;
            }

            // Update retailer's inventory
            if (st.checkProductInStorage(retailerID, productUPC, "Retailer")) {
                // Product exists, update quantity
                Product productInRetailer = st.getProductByUPCAndStorageID(productUPC, retailerID, "Retailer");
                int newRetailerQuantity = productInRetailer.getQuantity() + quantity;
                st.updateProductQuantityByProductUPC(productUPC, retailerID, newRetailerQuantity, "Retailer");
            } else {
                // Product doesn't exist, insert new record
                st.insertProductIntoStorage(retailerID, productUPC, quantity, "Retailer");
            }
        } else {
            System.out.printf("Product doesn't exist in warehouse %s\n", warehouseID);
            return false;
        }
        return true;
    }

    public boolean recordDistribution(String inventoryID, String warehouseID, String retailerID, int productUPC, int quantity, double price, String receivedBy) {
        Connection connection = DatabaseUtils.getConnection();

        String sql = "INSERT INTO Inventory (InventoryID, ProductUPC, Quantity, Price, SupplierID, RetailerID, InventoryType, InventoryTime, remarks, receivedBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventoryID);
            preparedStatement.setInt(2, productUPC);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setNull(5, Types.VARCHAR); // SupplierID is null for distribution to retailer
            preparedStatement.setString(6, retailerID);
            preparedStatement.setString(7, "Distribution");
            preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(9, "Distributed from warehouse " + warehouseID);
            preparedStatement.setString(10, receivedBy);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNewInventoryID() {
        Connection connection = DatabaseUtils.getConnection();
        String sql =    "SELECT MAX(InventoryID) " +
                        "AS MAX_InventoryID " +
                        "FROM Inventory";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String maxID = resultSet.getString("MAX_InventoryID");
                if (maxID != null) {
                    int idNum = Integer.parseInt(maxID.replace("I", "")) + 1;
                    return "I" + String.format("%03d", idNum);
                } else {
                    return "I001";
                }
            } else {
                return "I001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
