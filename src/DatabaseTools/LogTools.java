package DatabaseTools;

import Database.DatabaseUtils;
import Entity.Log;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogTools {
    public static void insertLog(String staffID){
        String sql = "INSERT INTO log (Time, StaffID) VALUES (?,?)";

        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, staffID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static List<Log> getLogList() {
        String sql = "SELECT * FROM log";
        List<Log> logList = new ArrayList<>();
        Connection connection = DatabaseUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logList.add(new Log(
                        resultSet.getTimestamp("Time").toLocalDateTime(),
                        resultSet.getString("StaffID")
                ));
            }
            if (logList.isEmpty()) {
                return null;
            }
            return logList;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
