package DatabaseTools;

import DataAccessObject.TransferDAO;
import DataAccessObject.TransferSet;
import Database.DatabaseUtils;
import Entity.Product;
import Model.Dimension;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.Instant;


public class StorageTools {

    // get the Product List by insert Warehouse ID
    public List<Product> getProductListByWarehouseID(String warehouseID) {

        // get connection to the array
        Connection connection = DatabaseUtils.getConnection();

        // set the sql query to get the product follow the warehouse ID
        String sql =    "SELECT * " +
                        "FROM product p " +
                        "JOIN storage s " +
                        "ON s.ProductUPC = p.ProductUPC " +
                        "WHERE s.WarehouseID = ?";

        List<Product> productList = new ArrayList<>();
        // try to get run the sql query
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set the warehouseID from the parameter
            preparedStatement.setString(1, warehouseID);

            //get the result from the qul query
            ResultSet resultSet = preparedStatement.executeQuery();

            TransferDAO transferDAO = new TransferDAO();
            // loop to get the product and add into the product list
            while (resultSet.next()) {
                if(resultSet.getInt("ProductQuantity")<=0) {
                    continue;
                }
                // If not skipping, add the product to the productList
                productList.add(new Product(
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
                        resultSet.getInt("Quantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
                ));
            }

            //return list
            return productList;

        }catch(SQLException e) {
            // catch exception if unsuccess to get the query
            throw new RuntimeException(e);
        }
    }

    // get the Product List by insert Warehouse ID
    public List<Product> getProductListByWarehouseIDAndException(String warehouseID, List<TransferSet> transferList) {

        // get connection to the array
        Connection connection = DatabaseUtils.getConnection();

        // set the sql query to get the product follow the warehouse ID
        String sql =    "SELECT * " +
                "FROM product p " +
                "JOIN storage s " +
                "ON s.ProductUPC = p.ProductUPC " +
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
            while (resultSet.next()) {
                boolean skip = false;  // Flag to check if UPC should be skipped

                if(resultSet.getInt("Quantity")<=0) {
                    continue;
                }

                for (TransferSet transferSet : transferList) {
                    if (transferSet.getProductUPC().equals(resultSet.getString("ProductUPC"))) {
                        skip = true;  // Set flag to skip adding this product
                        break;  // Exit the loop since we found a match
                    }
                }

                // If skip is true, continue to the next result
                if (skip) {
                    continue;
                }

                // If not skipping, add the product to the productList
                productList.add(new Product(
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
                        resultSet.getInt("Quantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()
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
                        "FROM product p " +
                        "JOIN storage s " +
                        "ON p.ProductUPC = s.ProductUPC " +
                        "WHERE p.ProductName = ? " +
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
                        resultSet.getInt("Quantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime());
            }else {
                System.out.println("Product not found");
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return product;
    }

    // get product by Product SKU
    public Product getProductByUPCANDWarehouseID(String productUPC, String WarehouseID) {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create product object to return
        Product product = null;
        //set the sql query
        String sql =    "SELECT * " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.ProductUPC = s.ProductUPC " +
                "WHERE s.ProductUPC = ? " +
                "AND s.WarehouseID = ?";
        // try to get the product
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, productUPC);
            preparedStatement.setString(2, WarehouseID);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            // create product if there have
            if(resultSet.next()) {
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
                        resultSet.getInt("Quantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime());
            }else {
                System.out.println("Product not found");
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return product;
    }

    //update the new quantity to the database by using productUPC
    public void updateProductQuantityByProductSKU(String productUPC, String warehouseID, int quantity){

        // get Connection from the database
        Connection connection = DatabaseUtils.getConnection();

        // set sql query
        String sql =    "UPDATE product p " +
                        "JOIN storage s " +
                        "ON p.ProductUPC = s.ProductUPC " +
                        "SET s.Quantity = ? , p.ProductUpdatedAt = ? " +
                        "WHERE s.ProductUPC = ? " +
                        "AND s.WarehouseID = ?";

        try{
            //prepare the sql query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // input the variable from the parameter
            preparedStatement.setInt(1, quantity);
            preparedStatement.setTimestamp(2, Timestamp.from(Instant.now()));
            preparedStatement.setString(3, productUPC);
            preparedStatement.setString(4, warehouseID);

            //run the sql query
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            // throw exception when error occur
            throw new RuntimeException(e);
        }

    }

    public void insertStorageForWarehouseProduct(String WarehouseID, String ProductUPC, int Quantity) {
        // get connection to database
        Connection connection = DatabaseUtils.getConnection();
        // sql string
        String sql = "INSERT INTO storage(StorageID, ProductUPC, WarehouseID, Quantity) VALUES (?,?,?,?)";
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, getNewStorageID());
           preparedStatement.setString(2, ProductUPC);
           preparedStatement.setString(3, WarehouseID);
           preparedStatement.setInt(4, Quantity);

           preparedStatement.executeUpdate();

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNewStorageID() {
        // get connection to database
        Connection connection = DatabaseUtils.getConnection();
        String storageID = null;
        //sql string
        String sql = "SELECT MAX(StorageID) AS MAX_STORAGE_ID FROM storage";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return "STG" + String.format("%03d", (Integer.parseInt(resultSet.getString("MAX_STORAGE_ID").replace("STG", "")) + 1));
            }else {
                return storageID = "STG001";
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
