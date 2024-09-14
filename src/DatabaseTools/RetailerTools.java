package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Retailer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetailerTools {
    public static String retrieveMaxRetailerID(){
        String sql = "SELECT MAX(RetailerID) FROM Retailer";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }else{
                return "RET001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertRetailer(Retailer retailer) {
        String sql = "INSERT INTO Retailer VALUES (?,?,?,?,?)";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, retailer.getRetailerId());


            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Retailer added successfully!");
                System.out.println("Retailer ID: " + retailer.getRetailerId());
            } else {
                System.out.println("Retailer added failed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
