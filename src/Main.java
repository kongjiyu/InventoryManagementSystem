import Entity.Staff;
import Database.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //open database object
        DatabaseUtils db = new DatabaseUtils();

        //connect database
        Connection connection = db.getConnection();

        try {
            //create prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM staff");

            //execute query and get result
            ResultSet resultSet = preparedStatement.executeQuery();


            Staff[] staff = new Staff[10];

            //get result
            while (resultSet.next()) {
                resultSet.getString("StaffName");
                resultSet.getInt("StaffID");
                resultSet.getDate("DateOfBirth");
            }

            //close database
            connection.close();
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
