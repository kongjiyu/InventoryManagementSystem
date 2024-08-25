package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import java.sql.*;
import java.util.*;

public class StorageTools {

    // get the Product List by insert Warehouse ID
    public List<Product> getProductListByWarehouseID(String warehouseID) {

        // get connection to the array
        Connection connection = DatabaseUtils.getConnection();

        // set the sql query to get the product follow the warehouse ID
        String sql =    "SELECT * " +
                        "FROM product p " +
                        "JOIN storage s " +
                        "ON s.ProductSKU = p.ProductSKU " +
                        "WHERE s.WarehouseID = ?";

        List<Product> productList = new ArrayList<>();
        // try to get run the sql query
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set the warehouseID from the parameter
            preparedStatement.setString(1, warehouseID);

            //get the result from the qul query
            ResultSet resultSet = preparedStatement.executeQuery();

            // loop to get the product and add into the product list
            while(resultSet.next()) {
                productList.add(new Product(
                        resultSet.getString("ProductSKU"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductDesc"),
                        resultSet.getString("ProductCategory"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getDouble("ProductWeight"),
                        resultSet.getString("ProductDimension"),
                        resultSet.getInt("ProductQuantity"),
                        resultSet.getTimestamp("ProductUpdatedAt")
                ));
            }

            //return list
            return productList;

        }catch(SQLException e) {
            // catch exception if unsuccess to get the query
            throw new RuntimeException(e);
        }
    }

    // get product by Product Name
    public Product getProductByNameANDWarehouseID(String productName, String WarehouseID) {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create product object to return
        Product product = null;
        //set the sql query
        String sql =    "SELECT * " +
                        "FROM product p J" +
                        "OIN storage s " +
                        "ON p.ProductSKU = s.ProductSKU " +
                        "WHERE s.ProductName = ? " +
                        "AND s.WarehouseID = ?";
        // try to get the product
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, WarehouseID);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create product if there have
            if(resultSet.next()) {
                product = new Product(
                        resultSet.getString("ProductSKU"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductDesc"),
                        resultSet.getString("ProductCategory"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getDouble("ProductWeight"),
                        resultSet.getString("ProductDimension"),
                        resultSet.getInt("ProductQuantity"),
                        resultSet.getTimestamp("ProductUpdatedAt"));
            }else {
                System.out.println("Product not found");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return product;
    }

    // get product by Product SKU
    public Product getProductBySKUANDWarehouseID(String productSKU, String WarehouseID) {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create product object to return
        Product product = null;
        //set the sql query
        String sql =    "SELECT * " +
                "FROM product p J" +
                "OIN storage s " +
                "ON p.ProductSKU = s.ProductSKU " +
                "WHERE s.ProductSKU = ? " +
                "AND s.WarehouseID = ?";
        // try to get the product
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, productSKU);
            preparedStatement.setString(2, WarehouseID);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create product if there have
            if(resultSet.next()) {
                product = new Product(
                        resultSet.getString("ProductSKU"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductDesc"),
                        resultSet.getString("ProductCategory"),
                        resultSet.getDouble("ProductPrice"),
                        resultSet.getDouble("ProductWeight"),
                        resultSet.getString("ProductDimension"),
                        resultSet.getInt("ProductQuantity"),
                        resultSet.getTimestamp("ProductUpdatedAt"));
            }else {
                System.out.println("Product not found");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return product;
    }

    //update the new quantity to the database by using productSKU
    public void updateProductQuantityByProductSKU(String productSKU, String warehouseID, int quantity){

        // get Connection from the database
        Connection connection = DatabaseUtils.getConnection();

        // set sql query
        String sql =    "UPDATE storage s " +
                        "JOIN product p " +
                        "ON p.ProductSKU = s.ProductSKU" +
                        "SET p.Quantity = ? " +
                        "WHERE s.ProductSKU = ? " +
                        "AND s.WarehouseID = ?";

        try{
            //prepare the sql query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // inpu the variable from the parameter
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, productSKU);
            preparedStatement.setString(3, warehouseID);

            //run the sql query
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            // throw exception when error occur
            throw new RuntimeException(e);
        }

    }
}
