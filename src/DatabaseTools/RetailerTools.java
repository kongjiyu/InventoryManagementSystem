package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import Entity.Retailer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Retailer> retrieveAllRetailers(){
        ArrayList<Retailer> retailers = new ArrayList<>();
        String sql = "SELECT * FROM Retailer";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                retailers.add(new Retailer(
                    resultSet.getString("RetailerID"),
                    resultSet.getString("RetailerName"),
                    resultSet.getString("RetailerAddress"),
                    resultSet.getString("RetailerPhone"),
                    resultSet.getString("RetailerEmail")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retailers;
    }

    public static boolean deleteRetailer(String retailerID) {
        String sql = "DELETE FROM Retailer WHERE RetailerID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, retailerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Retailer retrieveRetailer(String retailerID) {
        Retailer retailer = null;
        String sql = "SELECT * FROM Retailer WHERE RetailerID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, retailerID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                retailer = new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retailer;
    }
}
