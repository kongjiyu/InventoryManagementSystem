import Database.DatabaseUtils;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();
        System.out.print(connection);
    }


}
