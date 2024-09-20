package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Inventory;

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

}
