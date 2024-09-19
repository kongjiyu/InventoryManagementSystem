package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Staff;
import Entity.Admin;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StaffTools {
    public static boolean checkUsername(String username){
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffUsername = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkID(String staffID){
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, staffID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStaffID(String username){
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffUsername = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("StaffID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String username,String password){
        Staff staff = new Staff();

        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffUsername = ? AND Password=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkAdmin(Staff staff){
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffID = ? AND Password=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, staff.getStaffID());
            preparedStatement.setString(2, staff.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            int adminPrivilege = resultSet.getInt("AdminPrivilege");
            if(adminPrivilege >= 0 && adminPrivilege <= 3){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean registerStaff(Staff staff){
        //get connection
        Connection connection = DatabaseUtils.getConnection();
        //set the sql statement
        String sql = "INSERT INTO staff VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set the parameters
            preparedStatement.setString(1, staff.getStaffID());
            preparedStatement.setString(2, staff.getUsername());
            preparedStatement.setString(3, staff.getPassword());
            preparedStatement.setString(4, staff.getStaffIC());
            preparedStatement.setString(5, staff.getName());
            preparedStatement.setInt(6, staff.getAge());
            preparedStatement.setDate(7, Date.valueOf(staff.getHireDate()));
            preparedStatement.setDate(8, Date.valueOf(staff.getBirthDate()));
            preparedStatement.setDouble(9, staff.getSalary());
            preparedStatement.setString(10, staff.getEmail());
            preparedStatement.setString(11, staff.getPhone());
            preparedStatement.setString(12, staff.getAddress());
            preparedStatement.setString(14, staff.getWarehouseID());
            //check is the staff admin
            if(staff instanceof Admin){
                preparedStatement.setInt(13, ((Admin) staff).getPrivilege());
            }else{
                preparedStatement.setNull(13, Types.INTEGER);
            }

            int result = preparedStatement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getStaffWarehouseID(String username) {
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffUsername = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("WarehouseID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getStaffPrivilege(String username) {
        Connection connection = DatabaseUtils.getConnection();
        String sql = "SELECT * FROM staff WHERE StaffUsername = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AdminPrivilege");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Staff> retrieveAllStaff(){
        ArrayList<Staff> staffs = new ArrayList<>();
        String sql = "SELECT * FROM staff";

        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getInt("AdminPrivilege")>=0) {
                    staffs.add(new Admin(
                            resultSet.getString("StaffID"),
                            resultSet.getString("StaffUsername"),
                            resultSet.getString("Password"),
                            resultSet.getString("StaffIC"),
                            resultSet.getString("StaffName"),
                            resultSet.getInt("StaffAge"),
                            resultSet.getDate("StaffHireDate").toLocalDate(),
                            resultSet.getDate("StaffBirthDate").toLocalDate(),
                            resultSet.getDouble("StaffSalary"),
                            resultSet.getString("StaffEmail"),
                            resultSet.getString("StaffPhone"),
                            resultSet.getString("StaffAddress"),
                            true,
                            resultSet.getInt("AdminPrivilege"),
                            resultSet.getString("WarehouseID")
                    ));
                }else {
                    staffs.add(new Staff(
                            resultSet.getString("StaffID"),
                            resultSet.getString("StaffUsername"),
                            resultSet.getString("Password"),
                            resultSet.getString("StaffIC"),
                            resultSet.getString("StaffName"),
                            resultSet.getInt("StaffAge"),
                            resultSet.getDate("StaffHireDate").toLocalDate(),
                            resultSet.getDate("StaffBirthDate").toLocalDate(),
                            resultSet.getDouble("StaffSalary"),
                            resultSet.getString("StaffEmail"),
                            resultSet.getString("StaffPhone"),
                            resultSet.getString("StaffAddress"),
                            false,
                            resultSet.getString("WarehouseID")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staffs;
    }

    public static Staff retrieveStaff(String StaffID){
        Staff staff = new Staff();
        String sql = "SELECT * FROM staff WHERE StaffID = ?";

        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, StaffID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                staff = new Staff(
                        resultSet.getString("StaffID"),
                        resultSet.getString("StaffUsername"),
                        resultSet.getString("Password"),
                        resultSet.getString("StaffIC"),
                        resultSet.getString("StaffName"),
                        resultSet.getInt("StaffAge"),
                        resultSet.getDate("StaffHireDate").toLocalDate(),
                        resultSet.getDate("StaffBirthDate").toLocalDate(),
                        resultSet.getDouble("StaffSalary"),
                        resultSet.getString("StaffEmail"),
                        resultSet.getString("StaffPhone"),
                        resultSet.getString("StaffAddress"),
                        resultSet.getInt("AdminPrivilege") == 1,
                        resultSet.getString("WarehouseID")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return staff;
    }

    public static void updateStaff(Staff staff) {
        // SQL query to update all attributes of the Staff table
        String sql = "UPDATE Staff SET " +
                "StaffUsername = ?, " +
                "Password = ?, " +
                "StaffIC = ?, " +
                "StaffName = ?, " +
                "StaffAge = ?, " +
                "StaffHireDate = ?, " +
                "StaffBirthDate = ?, " +
                "StaffSalary = ?, " +
                "StaffEmail = ?, " +
                "StaffPhone = ?, " +
                "StaffAddress = ?, " +
                "AdminPrivilege = ?, " +
                "WarehouseID = ? " +
                "WHERE StaffID = ?";

        Connection connection = DatabaseUtils.getConnection(); // Assume DatabaseUtils provides a connection to the database

        try {
            // Prepare the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set all the staff attributes in the SQL query
            preparedStatement.setString(1, staff.getUsername());
            preparedStatement.setString(2, staff.getPassword());
            preparedStatement.setString(3, staff.getStaffIC());
            preparedStatement.setString(4, staff.getName());
            preparedStatement.setInt(5, staff.getAge());
            preparedStatement.setDate(6, java.sql.Date.valueOf(staff.getHireDate()));  // Assuming LocalDate for hire date
            preparedStatement.setDate(7, java.sql.Date.valueOf(staff.getBirthDate()));  // Assuming LocalDate for birth date
            preparedStatement.setDouble(8, staff.getSalary());
            preparedStatement.setString(9, staff.getEmail());
            preparedStatement.setString(10, staff.getPhone());
            preparedStatement.setString(11, staff.getAddress());
            if(staff instanceof Admin){
                preparedStatement.setInt(12, ((Admin) staff).getPrivilege());
            }else{
                preparedStatement.setNull(12, Types.INTEGER);
            }
            preparedStatement.setString(13, staff.getWarehouseID());
            preparedStatement.setString(14, staff.getStaffID());  // Set the StaffID to identify the row to update

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Staff record updated successfully.");
            } else {
                System.out.println("Failed to update staff record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the staff record.");
        }
    }

    // Function to delete the staff from the database
    public static void deleteStaff(String staffID) {
        String sql = "DELETE FROM Staff WHERE StaffID = ?";
        Connection connection = DatabaseUtils.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, staffID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Staff deleted successfully.");
            } else {
                System.out.println("Failed to delete staff.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while deleting the staff.");
        }
    }
}
