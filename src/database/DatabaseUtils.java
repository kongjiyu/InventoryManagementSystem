package database;

import java.sql.*;

public class DatabaseUtils {
    public Connection connection;

    public Connection getConnection() {
        final String DBURL = "jdbc:mysql://localhost:3306/inventory"; //replace the string with your local host url
        final String DBUSER = "root"; //replace the username String with your username (default is root)
        final String DBPASSWORD = "kongjiyu"; //replace the password String with your password
        try {
            DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
            System.out.println("Connect Successful");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String query) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
