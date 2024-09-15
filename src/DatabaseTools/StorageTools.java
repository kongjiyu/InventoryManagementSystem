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
                    if (transferSet.getProductUPC() == (resultSet.getInt("ProductUPC"))) {
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
        Connection connection = DatabaseUtils.getConnection();
        List<Product> productList = new ArrayList<>();
        Product product = null;

        String sql = "SELECT * " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.ProductUPC = s.ProductUPC " +
                "WHERE p.ProductName LIKE ? " +
                "AND s.WarehouseID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + productName + "%");
            preparedStatement.setString(2, WarehouseID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(new Product(
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
                        resultSet.getInt("Quantity"),
                        resultSet.getTimestamp("ProductUpdatedAt").toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (productList.isEmpty()) {
            System.out.println("Product not found!");
            return null;
        } else if (productList.size() == 1) {
            return productList.get(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalIndex = productList.size();
            final int productsPerPage = 5;
            int page = 0;
            int maxPages = (totalIndex - 1) / productsPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * productsPerPage;
                int endIndex = Math.min(startIndex + productsPerPage, totalIndex);

                // Display products on the current page
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("=========================================================================================================");
                System.out.printf("|%-3s|%-15s|%-20s|%-20s|%-20s|%-20s|\n", "No.", "Product UPC", "Product Name", "Product Category", "Product Price", "Product Quantity");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("=========================================================================================================");
                    System.out.printf("|%-3d|%-15s|%-20s|%-20s|RM%-18.2f|%-20d|\n",
                            count,
                            productList.get(i).getUPC(),
                            productList.get(i).getName(),
                            productList.get(i).getCategory(),
                            productList.get(i).getPrice(),
                            productList.get(i).getQuantity());
                    count++;
                }
                System.out.println("=========================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total products: %d\n", totalIndex);
                System.out.println("[\"A\" for last page]\t\t\t\t\t\t[\"Q\" to exit]\t\t\t\t\t\t[\"D\" for next page]");
                System.out.print("Input: ");

                String input = scanner.nextLine().trim();
                if (input.length() == 1) {
                    char option = input.charAt(0);

                    switch (option) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            if (Character.isDigit(option)) {
                                int num = Character.getNumericValue(option);
                                // Ensure that the selected number is within the displayed product range
                                if (num >= 1 && num <= (endIndex - startIndex)) {
                                    return productList.get(startIndex + num - 1);
                                }
                            }
                            System.out.println("Invalid input. Please enter a valid option.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                }

            } while (!exit);
            return null;
        }
    }

    // get product by Product SKU
    public Product getProductByUPCANDWarehouseID(int productUPC, String WarehouseID) {
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
            preparedStatement.setInt(1, productUPC);
            preparedStatement.setString(2, WarehouseID);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            // create product if there have
            if(resultSet.next()) {
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
    public void updateProductQuantityByProductSKU(int productUPC, String warehouseID, int quantity){

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
            preparedStatement.setInt(3, productUPC);
            preparedStatement.setString(4, warehouseID);

            //run the sql query
            preparedStatement.executeUpdate();
        }catch(SQLException e) {
            // throw exception when error occur
            throw new RuntimeException(e);
        }

    }

    public void insertStorageForWarehouseProduct(String WarehouseID, int ProductUPC, int Quantity) {
        // get connection to database
        Connection connection = DatabaseUtils.getConnection();
        // sql string
        String sql = "INSERT INTO storage(StorageID, ProductUPC, WarehouseID, Quantity) VALUES (?,?,?,?)";
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1, getNewStorageID());
           preparedStatement.setInt (2, ProductUPC);
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
