import database.DatabaseUtils;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseUtils db = new DatabaseUtils();
        Connection con = db.getConnection();
    }
}
