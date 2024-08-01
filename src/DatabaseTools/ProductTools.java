package DatabaseTools;

import Entity.Product;
import Database.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductTools {
    public static Product retrieveProduct(String SKU){
        //create a product
        Product product = null;

        //sql select statement to retrieve product that have the SKU
        String sql = "SELECT * FROM Product WHERE SKU = ?";

        //get connection to database
        Connection connection = DatabaseUtils.getConnection();
        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameter
            preparedStatement.setString(1, SKU);

            //get result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product = new Product(
                        resultSet.getString("ProductSKU"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductDesc"),
                        resultSet.getString("ProductCategory"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getDouble("ProductWeight"),
                        resultSet.getString("ProductDimension"),
                        resultSet.getInt("ProductQuantity"),
                        resultSet.getDate("ProductExpDate"),
                        resultSet.getTimestamp("ProductUpdatedAt")
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}
