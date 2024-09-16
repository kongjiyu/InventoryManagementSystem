package DatabaseTools;

import Database.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryTools {

    public String getNewInventoryID() {
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT MAX(InventoryID) AS MAX_InventoryID FROM Inventory";

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
