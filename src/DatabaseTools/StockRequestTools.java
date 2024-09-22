package DatabaseTools;

import Database.DatabaseUtils;
import Entity.StockRequest;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StockRequestTools {

    public static StockRequest stockRequestLog(String RequestID) {
        StockRequest stockRequest = null;

        String sql = "SELECT * FROM StockRequest WHERE RequestID = ?";

        Connection connection = DatabaseUtils.getConnection();
        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set parameter
            preparedStatement.setString(1, RequestID);

            //get result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                stockRequest = new StockRequest(
                        resultSet.getString("RequestID"),
                        resultSet.getString("ProductUPC"),
                        resultSet.getInt("Quantity"),
                        resultSet.getString("requestedBy"),
                        resultSet.getString("warehouseID"),
                        resultSet.getDate("requestDate").toLocalDate()
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockRequest;
    }

    public static void insertStockRequest(StockRequest request) {
        String sql = "INSERT INTO StockRequest (RequestID, ProductUPC, Quantity, requestedBy, warehouseID, requestDate) VALUES (?,?,?,?,?,?)";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, request.getRequestId());
            preparedStatement.setString(2, request.getProductUPC());
            preparedStatement.setInt(3, request.getQuantity());
            preparedStatement.setString(4, request.getRequestBy());
            preparedStatement.setString(5, request.getWarehouseId());
            preparedStatement.setTimestamp(6, Timestamp.from(Instant.now()));

            int result = preparedStatement.executeUpdate();
            System.out.println(result + " Stock Request inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String retrieveMaxRequestID(){
        String sql = "SELECT MAX(RequestID) FROM StockRequest";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String maxID = resultSet.getString(1);
                if (maxID != null) {
                    int num = Integer.parseInt(maxID.substring(3));
                    return String.format("REQ%03d", num + 1);
                } else {
                    return "REQ001";
                }
            } else {
                return "REQ001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean ValidateWarehouseId(String warehouseId) {
        String sql = "SELECT warehouseID FROM Warehouse WHERE warehouseID = ?";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, warehouseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean ValidateProductUPC(String productUPC) {
        String sql = "SELECT ProductUPC FROM Product WHERE ProductUPC = ?";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productUPC);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<StockRequest> retrieveAllStockRequests() {
        String sql = "SELECT * FROM StockRequest";
        List<StockRequest> stockRequests = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stockRequests.add(new StockRequest(
                        resultSet.getString("RequestID"),
                        resultSet.getString("ProductUPC"),
                        resultSet.getInt("Quantity"),
                        resultSet.getString("requestedBy"),
                        resultSet.getString("warehouseID"),
                        resultSet.getDate("requestDate").toLocalDate()

                ));
            }
            if (stockRequests.isEmpty()) {
                return null;
            }
            return stockRequests;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}