package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Supplier;
import Entity.Supplier;
import Entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierTools {
    public static String retrieveMaxSupplierID(){
        String sql = "SELECT MAX(SupplierID) FROM Supplier";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }else{
                return "SUP001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSupplier(Supplier supplier){
        String sql = "INSERT INTO Supplier VALUES (?,?,?,?,?)";
        Connection connection = DatabaseUtils.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, supplier.getSupplierId());


            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Supplier added successfully!");
                System.out.println("Supplier ID: " + supplier.getSupplierId());
            }else{
                System.out.println("Supplier added failed!");
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Supplier> retrieveAllSuppliers(){
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                suppliers.add(new Supplier(
                        resultSet.getString("SupplierID"),
                        resultSet.getString("SupplierName"),
                        resultSet.getString("SupplierAddress"),
                        resultSet.getString("SupplierPhone"),
                        resultSet.getString("SupplierEmail")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suppliers;
    }

    public static boolean deleteSupplier(String supplierID) {
        String sql = "DELETE FROM Supplier WHERE SupplierID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Supplier retrieveSupplier(String supplierID) {
        Supplier supplier = null;
        String sql = "SELECT * FROM Supplier WHERE SupplierID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplierID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                supplier = new Supplier(
                        resultSet.getString("SupplierID"),
                        resultSet.getString("SupplierName"),
                        resultSet.getString("SupplierAddress"),
                        resultSet.getString("SupplierPhone"),
                        resultSet.getString("SupplierEmail")
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return supplier;
    }

    public static ArrayList<Supplier> searchSuppliers(String field, String value) {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier WHERE " + field + " LIKE ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                suppliers.add(new Supplier(
                        resultSet.getString("SupplierID"),
                        resultSet.getString("SupplierName"),
                        resultSet.getString("SupplierAddress"),
                        resultSet.getString("SupplierPhone"),
                        resultSet.getString("SupplierEmail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while searching for suppliers.");
            e.printStackTrace();
        }

        return suppliers;
    }

}
