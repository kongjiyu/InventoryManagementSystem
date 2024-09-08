package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                return "S001";
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
}
