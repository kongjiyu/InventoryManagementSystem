package DatabaseTools;

import Database.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTools {
    public static int getNewOrderID(){
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT MAX(orderID) FROM orders";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int orderID = resultSet.getInt(1);
            if(orderID == 0){
                return 1;
            }else{
                return orderID + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
