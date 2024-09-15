package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import Entity.Transfer;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;

public class TransferTools {

    // transfer stock from first warehouse to another warehouse with product id and quantity
    public boolean transferStock(String firstWarehouseID, String secondWarehouseID, int ProductUPC, int quantity) {
        // create object
        StorageTools st = new StorageTools();
        ProductTools pt = new ProductTools();
        // check the product is in the warehouse or not
        if (checkProductInWarehouse(firstWarehouseID, ProductUPC)){
            Product productInFirstWarehouse = st.getProductByUPCANDWarehouseID(ProductUPC, firstWarehouseID);
            // change the first warehouse product quantity
            if (productInFirstWarehouse.getQuantity() >= quantity) {
                productInFirstWarehouse.setQuantity(productInFirstWarehouse.getQuantity() - quantity);
                st.updateProductQuantityByProductSKU(ProductUPC, firstWarehouseID, productInFirstWarehouse.getQuantity());
            }else {
                // if not enough stock
                System.out.printf("Not enough stock at Warehouse %s\n", firstWarehouseID);
                return false;
            }
            // check if there is product in the warehouse, then update quantity only.
            if(checkProductInWarehouse(secondWarehouseID, ProductUPC)){
                Product productInSecondWarehouse = st.getProductByUPCANDWarehouseID(ProductUPC, secondWarehouseID);
                st.updateProductQuantityByProductSKU(ProductUPC, secondWarehouseID, productInSecondWarehouse.getQuantity()+ quantity);
            }else {
                // if don't have, need to insert new product inside
                st.insertStorageForWarehouseProduct(secondWarehouseID, ProductUPC, quantity);
            }
        }else {
            System.out.printf("Product doesn't exist in warehouse %s\n", firstWarehouseID);
            return false;
        }
        return true;
    }

    // check warehouse have certain product or not
    public boolean checkProductInWarehouse(String warehouseID, int productUPC) {
        Connection connection = DatabaseUtils.getConnection();

        String sql =    "SELECT * " +
                        "FROM product p " +
                        "JOIN storage s " +
                        "ON p.ProductUPC = s.ProductUPC " +
                        "WHERE s.WarehouseID = ? " +
                        "AND s.ProductUPC = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, warehouseID);
            preparedStatement.setInt(2, productUPC);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }else {
                return false;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // record transfer record
    public boolean recordTransfer(String TransferID, String FromWarehouse, String ToWarehosue, String ProductUPC, int Quantity){
        Connection connection = DatabaseUtils.getConnection();

        String sql = "INSERT INTO transfer VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, TransferID);
            preparedStatement.setString(2, FromWarehouse);
            preparedStatement.setString(3, ToWarehosue);
            preparedStatement.setString(4, ProductUPC);
            preparedStatement.setInt(5, Quantity);
            preparedStatement.setDate(6, new java.sql.Date(Instant.now().toEpochMilli()));
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // get new transferID
    public String getNewTransferID(){
        // get connection to database
        Connection connection = DatabaseUtils.getConnection();
        String transferID = null;
        //sql string
        String sql = "SELECT MAX(TransferID) AS MAX_TransferID FROM transfer";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return ("T" + String.format("%03d", (Integer.parseInt(resultSet.getString("MAX_TransferID").replace("T", "")) + 1)));
            }else {
                return "T001";
            }

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
