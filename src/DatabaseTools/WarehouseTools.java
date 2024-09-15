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
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

    public Warehouse getWarehouseByList() {
        // get connection with database
        Connection connection = DatabaseUtils.getConnection();
        // create warehouse list
        List<Warehouse> warehouseList = new ArrayList<>();
        // create warehouse object to return
        Warehouse warehouse = null;
        //set the sql query
        String sql =    "SELECT * " +
                "FROM warehouse ";
        // try to get the warehouse
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create warehouse if there have
            while(resultSet.next()) {
                warehouseList.add(new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail")));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        if (warehouseList.isEmpty()) {
            System.out.println("No Warehouse founded!");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }else if(warehouseList.size() == 1){
            return warehouseList.get(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalIndex = warehouseList.size();
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
                System.out.println("===================================================================================================================================================================================================");
                System.out.printf("|%-3s|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "No.", "Warehouse ID", "Warehouse Name", "Warehouse Address", "Warehouse Phone", "Warehouse Email");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("===================================================================================================================================================================================================");
                    System.out.printf("|%-3d|%-15s|%-20s|%-100s|%-20s|%-30s|\n",
                            count,
                            warehouseList.get(i).getWarehouseId(),
                            warehouseList.get(i).getWarehouseName(),
                            warehouseList.get(i).getWarehouseAddress(),
                            warehouseList.get(i).getWarehousePhone(),
                            warehouseList.get(i).getWarehouseEmail());
                    count++;
                }
                System.out.println("===================================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total warehouse: %d\n", totalIndex);
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
                                    return warehouseList.get(startIndex + num - 1);
                                }
                            }
                            System.out.println("Invalid input. Please enter a valid option.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                }

            } while (!exit);
        }
        return warehouse;
    }

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
        // create warehouse list
        List<Warehouse> warehouseList = new ArrayList<>();
        // create warehouse object to return
        Warehouse warehouse = null;
        //set the sql query
        String sql =    "SELECT * " +
                "FROM warehouse " +
                "WHERE WarehouseName LIKE ?";
        // try to get the warehouse
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // input the query variable
            preparedStatement.setString(1, "%"+ name + "%");
            // get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            //create warehouse if there have
            while(resultSet.next()) {
                warehouseList.add(new Warehouse(
                        resultSet.getString("WarehouseID"),
                        resultSet.getString("WarehouseName"),
                        resultSet.getString("WarehouseAddress"),
                        resultSet.getString("WarehousePhone"),
                        resultSet.getString("WarehouseEmail")));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        if (warehouseList.isEmpty()) {
            System.out.println("No Warehouse founded!");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }else if(warehouseList.size() == 1){
            return warehouseList.get(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalIndex = warehouseList.size();
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
                System.out.println("===================================================================================================================================================================================================");
                System.out.printf("|%-3s|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "No.", "Warehouse ID", "Warehouse Name", "Warehouse Address", "Warehouse Phone", "Warehouse Email");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("===================================================================================================================================================================================================");
                    System.out.printf("|%-3d|%-15s|%-20s|%-100s|%-20s|%-30s|\n",
                            count,
                            warehouseList.get(i).getWarehouseId(),
                            warehouseList.get(i).getWarehouseName(),
                            warehouseList.get(i).getWarehouseAddress(),
                            warehouseList.get(i).getWarehousePhone(),
                            warehouseList.get(i).getWarehouseEmail());
                    count++;
                }
                System.out.println("===================================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total warehouse: %d\n", totalIndex);
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
                                    return warehouseList.get(startIndex + num - 1);
                                }
                            }
                            System.out.println("Invalid input. Please enter a valid option.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                }

            } while (!exit);
        }
        return warehouse;
    }
}
