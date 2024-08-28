package DatabaseTools;

import Entity.Product;
import Model.Dimension;
import Database.DatabaseUtils;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductTools {
    public static Product retrieveProduct(String UPC){
        //create a product
        Product product = null;

        //sql select statement to retrieve product that have the UPC
        String sql = "SELECT * FROM Product WHERE ProductUPC = ?";

        //get connection to database
        Connection connection = DatabaseUtils.getConnection();
        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameter
            preparedStatement.setString(1, UPC);

            //get result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product = new Product(
                        resultSet.getString("ProductUPC"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductDesc"),
                        resultSet.getString("ProductCategory"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getDouble("ProductWeight"),
                        new Dimension(
                                resultSet.getDouble("ProductWidth"),
                                resultSet.getDouble("ProductLength"),
                                resultSet.getDouble("ProductHeight")
                        ),
                        resultSet.getInt("ProductQuantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public static ArrayList<String> retrieveAllCategories(){
        ArrayList<String> categories = new ArrayList<>();

        String sql = "SELECT ProductCategory FROM Product";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while(resultSet.next()){
                categories.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(categories);
        return categories;
    }

    public static void insertProduct(Product product){
        String sql = "INSERT INTO Product (ProductName, ProductDesc, ProductCategory, ProductPrice, ProductWeight, ProductWidth, ProductLength, ProductHeight, ProductQuantity, ProductUpdatedAt) VALUES (?,?,?,?,?,?,?,?,?,?)";
        String getID = "SELECT LAST_INSERT_ID()";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDesc());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getWeight());
            preparedStatement.setDouble(6, product.getDimension().getWidth());
            preparedStatement.setDouble(7, product.getDimension().getLength());
            preparedStatement.setDouble(8, product.getDimension().getHeight());
            preparedStatement.setInt(9, product.getQuantity());
            preparedStatement.setTimestamp(10, Timestamp.from(Instant.now()));

            int result = preparedStatement.executeUpdate();
            //return UPC if the product is inserted successfully
            if(result > 0){
                System.out.println("Product inserted successfully");
                preparedStatement = connection.prepareStatement(getID);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                System.out.println("The ID of the inserted item: " + resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
