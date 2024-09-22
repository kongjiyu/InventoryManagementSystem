package DatabaseTools;

import Entity.Product;
import Model.Dimension;
import Database.DatabaseUtils;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class ProductTools {
    public static Product retrieveProduct(int UPC){
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
            preparedStatement.setInt(1, UPC);

            //get result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product = new Product(
                        resultSet.getInt("ProductUPC"),
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

    public static ArrayList<Product> retrieveAllProducts(){
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                products.add(new Product(
                        resultSet.getInt("ProductUPC"),
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
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
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
        return categories;
    }

    public static void insertProduct(Product product){
        String sql = "INSERT INTO Product (ProductName, ProductDesc, ProductCategory, ProductPrice, ProductWeight, ProductWidth, ProductLength, ProductHeight, ProductUpdatedAt) VALUES (?,?,?,?,?,?,?,?,?)";
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
            preparedStatement.setTimestamp(9, Timestamp.from(Instant.now()));

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

    public static boolean deleteProduct(int UPC){
        String closeForeignKeyChecks = "SET FOREIGN_KEY_CHECKS = 0;";
        String sql = "DELETE FROM Product WHERE ProductUPC = ?";
        String openForeignKeyChecks = "SET FOREIGN_KEY_CHECKS = 1;";

        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(closeForeignKeyChecks);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UPC);
            boolean result =  preparedStatement.executeUpdate() > 0;
            preparedStatement = connection.prepareStatement(openForeignKeyChecks);
            preparedStatement.execute();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateProduct(Product product){
        String sql =    "UPDATE product " +
                        "SET ProductName = ? , ProductDesc = ? , ProductCategory = ?, ProductPrice = ?, ProductWeight = ?, ProductWidth = ?, ProductLength = ?, ProductHeight = ?, ProductUpdatedAt = ?" +
                        "WHERE ProductUPC = ?;";
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
            preparedStatement.setTimestamp(9, Timestamp.from(Instant.now()));
            preparedStatement.setInt(10, product.getUPC());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Product> searchProducts(String field, String value) {
        ArrayList<Product> products = new ArrayList<>();
        String sqlField;

        // Validate the field to prevent SQL injection
        switch (field) {
            case "ProductUPC":
            case "ProductName":
            case "ProductCategory":
                sqlField = field;
                break;
            default:
                System.out.println("Invalid search field.");
                return products;
        }

        String sql = "SELECT * FROM Product WHERE " + sqlField + " LIKE ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (field.equals("ProductUPC")) {
                preparedStatement.setString(1, value);
            } else {
                preparedStatement.setString(1, "%" + value + "%");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("ProductUPC"),
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
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public static ArrayList<Product> searchProductsByRange(String field, double minValue, double maxValue) {
        ArrayList<Product> products = new ArrayList<>();
        String sqlField;

        // Validate the field to prevent SQL injection
        switch (field) {
            case "ProductPrice":
            case "ProductWeight":
                sqlField = field;
                break;
            default:
                System.out.println("Invalid search field.");
                return products;
        }

        String sql = "SELECT * FROM Product WHERE " + sqlField + " BETWEEN ? AND ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, minValue);
            preparedStatement.setDouble(2, maxValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("ProductUPC"),
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
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }
}
