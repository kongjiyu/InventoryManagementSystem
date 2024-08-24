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
        String sql = "INSERT INTO staff VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set the parameters
            preparedStatement.setString(1, staff.getStaffID());
            preparedStatement.setString(2, staff.getPassword());
            preparedStatement.setString(3, staff.getStaffIC());
            preparedStatement.setString(4, staff.getName());
            preparedStatement.setInt(5, staff.getAge());
            preparedStatement.setDate(6, Date.valueOf(staff.getHireDate()));
            preparedStatement.setDate(7, Date.valueOf(staff.getBirthDate()));
            preparedStatement.setDouble(8, staff.getSalary());
            preparedStatement.setString(9, staff.getEmail());
            preparedStatement.setString(10, staff.getPhone());
            preparedStatement.setString(11, staff.getAddress());
            //check is the staff admin
            if(staff instanceof Admin){
                preparedStatement.setInt(12, 1);
            }else{
                preparedStatement.setNull(12, Types.INTEGER);
            }

            int result = preparedStatement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
