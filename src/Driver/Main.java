package Driver;

import DataAccessObject.ProductDAO;
import DataAccessObject.StorageDAO;
import Database.DatabaseUtils;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();

        StorageDAO storageDAO = new StorageDAO();

        storageDAO.getProductUPC("W001");

    }
}
