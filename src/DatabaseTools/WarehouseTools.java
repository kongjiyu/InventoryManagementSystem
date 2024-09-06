package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Product;
import Entity.Warehouse;
import Model.Dimension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class WarehouseTools {

    public Warehouse getWarehouseById(String ID) {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create warehouse object to return
        Warehouse warehouse = null;
        //set the sql query
        String sql =    "SELECT * " +
                        "FROM warehouse " +
                        "WHERE WarehouseID = ?";
        // try to get the warehouse
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, ID);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create warehouse if there have
            if(resultSet.next()) {
                warehouse = new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail"));
            }else {
                System.out.println("Warehouse not found");
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return warehouse;
    }

    public Warehouse getWarehouseByName(String name) {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create warehouse object to return
        Warehouse warehouse = null;
        //set the sql query
        String sql =    "SELECT * " +
                "FROM warehouse " +
                "WHERE WarehouseName = ?";
        // try to get the warehouse
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, name);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create warehouse if there have
            if(resultSet.next()) {
                warehouse = new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail"));
            }else {
                System.out.println("Warehouse not found");
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return warehouse;
    }
}
