package Database;

import java.sql.*;
import java.util.Scanner;

public class DatabaseUtils {
    public static Connection connection;
    static String DBPASSWORD; //replace the password String with your password

    public static Connection getConnection() {
        Scanner scanner = new Scanner(System.in);
        final String DBURL = "jdbc:mysql://localhost:3306/inventory"; //replace the string with your local host url
        final String DBUSER = "root"; //replace the username String with your username (default is root)
        if(DBPASSWORD == null){
            System.out.print("Please enter database password: ");
            DBPASSWORD = scanner.nextLine();

            try {
                connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
                System.out.println("Connect Successful");
                return connection;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            return connection;
        }

    }
}
