package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Inventory;
import Entity.Product;

import java.sql.*;
import java.time.LocalDateTime;

public class InventoryTools {

    public String getNewInventoryID() {
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT MAX(InventoryID) AS MAX_InventoryID FROM Inventory";

        try{
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

    public void insertStockIn(Inventory inventory) {
        Connection connection = DatabaseUtils.getConnection();
        String sql = "INSERT INTO Inventory(InventoryID, ProductUPC, Quantity, Price, SupplierID, InventoryType, InventoryTime, remarks, receivedBy, WarehouseID) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventory.getId());
            preparedStatement.setInt(2, inventory.getProductUPC());
            preparedStatement.setInt(3, inventory.getQuantity());
            preparedStatement.setDouble(4, inventory.getPrice());
            preparedStatement.setString(5, inventory.getSupplierID());
            preparedStatement.setString(6, inventory.getInventoryType());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, inventory.getRemarks());
            preparedStatement.setString(9, inventory.getReceivedBy());
            preparedStatement.setString(10, inventory.getWarehouseID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
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

}
