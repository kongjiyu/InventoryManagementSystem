package Driver;

import DataAccessObject.ProductDAO;
import Database.DatabaseUtils;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();

        ProductDAO.createProduct();
    }
}
