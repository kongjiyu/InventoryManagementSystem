package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Staff;
import Entity.Admin;

import java.sql.*;

public class StaffTools {
    public boolean checkStaffID(String staffID){
        Staff staff = null;

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

    public boolean checkStaffAccount(String staffID, String password){
        return true;
    }



    public static boolean registerStaff(Staff staff){
        //get connection
        Connection connection = DatabaseUtils.getConnection();
        //set the sql statement
        String sql = "INSERT INTO staff VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            //check is the staff admin
            if(staff instanceof Admin){
                preparedStatement.setInt(13, 1);
            }else{
                preparedStatement.setNull(13, Types.INTEGER);
            }

            int result = preparedStatement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
