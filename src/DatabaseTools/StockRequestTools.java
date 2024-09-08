package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import Entity.Warehouse;
import Entity.StockRequest;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StockRequestTools {
    private List<StockRequest> stockRequestLog = new ArrayList<>();

    public void createStockRequest(StockRequest request) {
        stockRequestLog.add(request);
        System.out.println("Stock request created successfully.");
    }

    public List<StockRequest> getStockRequestLog() {
        return stockRequestLog;
    }

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

    public static void insertStockRequest(StockRequest request, Product product, Warehouse warehouse) {
        String sql = "INSERT INTO StockRequest (ProductUPC,Quantity,requestedBy,warehouseID,requestDate) VALUES (?,?,?,?,?)";
        String getID = "SELECT LAST_INSERT_ID()";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, product.getUPC());
            preparedStatement.setInt(2, request.getQuantity());
            preparedStatement.setString(3, request.getRequestBy());
            preparedStatement.setString(4, warehouse.getWarehouseId());
            preparedStatement.setTimestamp(5, Timestamp.from(Instant.now()));

            int result = preparedStatement.executeUpdate();
            //return UPC if the product is inserted successfully
            if (result > 0) {
                System.out.println("Successfully Request Stock");
                preparedStatement = connection.prepareStatement(getID);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                System.out.println("The ID of the inserted item: " + resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRequestId() {
        String lastRequestId = null;
        String newRequestId = null;

        String sql = "SELECT RequestID FROM StockRequest ORDER BY RequestID DESC LIMIT 1";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastRequestId = resultSet.getString("RequestID");
                String prefix = "REQ";
                String numericPart = lastRequestId.substring(prefix.length());
                int number = Integer.parseInt(numericPart);
                number++;
                newRequestId = String.format("%s%05d", prefix, number);
            } else {
                newRequestId = "REQ00001";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while generating request ID", e);
        }

        return newRequestId;
    }


}
