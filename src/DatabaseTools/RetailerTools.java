package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Retailer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RetailerTools {

    public static String retrieveMaxRetailerID(){
        String sql = "SELECT MAX(RetailerID) FROM Retailer";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String maxID = resultSet.getString(1);
                if (maxID == null) {
                    return "R001";
                } else {
                    // Assuming RetailerID is in the format R001, R002, etc.
                    int idNum = Integer.parseInt(maxID.substring(1)) + 1;
                    return String.format("R%03d", idNum);
                }
            } else {
                return "R001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertRetailer(Retailer retailer) {
        String sql = "INSERT INTO Retailer VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, retailer.getRetailerId());
            preparedStatement.setString(2, retailer.getRetailerName());
            preparedStatement.setString(3, retailer.getRetailerAddress());
            preparedStatement.setString(4, retailer.getRetailerPhone());
            preparedStatement.setString(5, retailer.getRetailerEmail());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Retailer added successfully!");
                System.out.println("Retailer ID: " + retailer.getRetailerId());
            } else {
                System.out.println("Failed to add retailer!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Retailer> retrieveAllRetailers(){
        ArrayList<Retailer> retailers = new ArrayList<>();
        String sql = "SELECT * FROM Retailer";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                retailers.add(new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retailers;
    }

    public static boolean deleteRetailer(String retailerID) {
        String sql = "DELETE FROM Retailer WHERE RetailerID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, retailerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Retailer retrieveRetailer(String retailerID) {
        Retailer retailer = null;
        String sql = "SELECT * FROM Retailer WHERE RetailerID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, retailerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                retailer = new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")
                );
            } else {
                System.out.println("Retailer not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retailer;
    }

    public static ArrayList<Retailer> searchRetailers(String field, String value) {
        ArrayList<Retailer> retailers = new ArrayList<>();
        String sql = "SELECT * FROM Retailer WHERE " + field + " LIKE ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + value + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                retailers.add(new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while searching for retailers.");
            e.printStackTrace();
        }
        return retailers;
    }

    public Retailer getRetailerByList() {
        Connection connection = DatabaseUtils.getConnection();
        List<Retailer> retailerList = new ArrayList<>();
        Retailer retailer = null;
        String sql = "SELECT * FROM Retailer";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                retailerList.add(new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        if (retailerList.isEmpty()) {
            System.out.println("No retailers found!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        } else if(retailerList.size() == 1){
            return retailerList.get(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalIndex = retailerList.size();
            final int retailersPerPage = 5;
            int page = 0;
            int maxPages = (totalIndex - 1) / retailersPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * retailersPerPage;
                int endIndex = Math.min(startIndex + retailersPerPage, totalIndex);

                // Display retailers on the current page
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-3s|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "No.", "Retailer ID", "Retailer Name", "Retailer Address", "Retailer Phone", "Retailer Email");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("===============================================================================================================================================================================================");
                    System.out.printf("|%-3d|%-15s|%-20s|%-100s|%-20s|%-30s|\n",
                            count,
                            retailerList.get(i).getRetailerId(),
                            retailerList.get(i).getRetailerName(),
                            retailerList.get(i).getRetailerAddress(),
                            retailerList.get(i).getRetailerPhone(),
                            retailerList.get(i).getRetailerEmail());
                    count++;
                }
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total retailers: %d\n", totalIndex);
                System.out.println("[\"A\" for previous page]\t\t\t[\"Q\" to exit]\t\t\t[\"D\" for next page]");
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
                                // Ensure that the selected number is within the displayed retailer range
                                if (num >= 1 && num <= (endIndex - startIndex)) {
                                    return retailerList.get(startIndex + num - 1);
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid option.");
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                }

            } while (!exit);
        }
        return retailer;
    }

    public Retailer getRetailerById(String ID) {
        Connection connection = DatabaseUtils.getConnection();
        Retailer retailer = null;
        String sql = "SELECT * FROM Retailer WHERE RetailerID = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                retailer = new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail"));
            } else {
                System.out.println("Retailer not found.");
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    throw new RuntimeException(ie);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return retailer;
    }

    public Retailer getRetailerByName(String name) {
        Connection connection = DatabaseUtils.getConnection();
        List<Retailer> retailerList = new ArrayList<>();
        String sql = "SELECT * FROM Retailer WHERE RetailerName LIKE ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                retailerList.add(new Retailer(
                        resultSet.getString("RetailerID"),
                        resultSet.getString("RetailerName"),
                        resultSet.getString("RetailerAddress"),
                        resultSet.getString("RetailerPhone"),
                        resultSet.getString("RetailerEmail")));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        if (retailerList.isEmpty()) {
            System.out.println("No retailers found!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        } else if(retailerList.size() == 1){
            return retailerList.get(0);
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalIndex = retailerList.size();
            final int retailersPerPage = 5;
            int page = 0;
            int maxPages = (totalIndex - 1) / retailersPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * retailersPerPage;
                int endIndex = Math.min(startIndex + retailersPerPage, totalIndex);

                // Display retailers on the current page
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-3s|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "No.", "Retailer ID", "Retailer Name", "Retailer Address", "Retailer Phone", "Retailer Email");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println("===============================================================================================================================================================================================");
                    System.out.printf("|%-3d|%-15s|%-20s|%-100s|%-20s|%-30s|\n",
                            count,
                            retailerList.get(i).getRetailerId(),
                            retailerList.get(i).getRetailerName(),
                            retailerList.get(i).getRetailerAddress(),
                            retailerList.get(i).getRetailerPhone(),
                            retailerList.get(i).getRetailerEmail());
                    count++;
                }
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total retailers: %d\n", totalIndex);
                System.out.println("[\"A\" for previous page]\t\t\t[\"Q\" to exit]\t\t\t[\"D\" for next page]");
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
                                // Ensure that the selected number is within the displayed retailer range
                                if (num >= 1 && num <= (endIndex - startIndex)) {
                                    return retailerList.get(startIndex + num - 1);
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid option.");
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                }

            } while (!exit);
        }
        return null;
    }
}
