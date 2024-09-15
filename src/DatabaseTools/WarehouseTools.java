package DatabaseTools;

import Database.DatabaseUtils;
import Driver.Utils;
import Entity.Warehouse;
import Entity.Warehouse;
import Entity.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class WarehouseTools {
    public static String retrieveMaxWarehouseID(){
        String sql = "SELECT MAX(WarehouseID) FROM Warehouse";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }else{
                return "W001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertWarehouse(Warehouse warehouse) {
        String sql = "INSERT INTO Warehouse VALUES (?,?,?,?,?)";
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, warehouse.getWarehouseId());


            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Warehouse added successfully!");
                System.out.println("Warehouse ID: " + warehouse.getWarehouseId());
            } else {
                System.out.println("Warehouse added failed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Warehouse> retrieveAllWarehouses(){
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM Warehouse";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                warehouses.add(new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warehouses;
    }

    public static boolean deleteWarehouse(String warehouseID) {
        String sql = "DELETE FROM Warehouse WHERE WarehouseID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, warehouseID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Warehouse retrieveWarehouse(String warehouseID) {
        Warehouse warehouse = null;
        String sql = "SELECT * FROM Warehouse WHERE WarehouseID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, warehouseID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                warehouse = new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail")
                );
            }else{
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warehouse;
    }

    public static ArrayList<Warehouse> searchWarehouses(String field, String value) {
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM Warehouse WHERE " + field + " LIKE ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                warehouses.add(new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while searching for warehouses.");
            e.printStackTrace();
        }

        return warehouses;
    }

}
