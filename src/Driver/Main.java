package Driver;

import DataAccessObject.ProductDAO;
import DataAccessObject.StaffDAO;
import DataAccessObject.StorageDAO;
import Database.DatabaseUtils;
import Entity.Product;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();
        StorageDAO sd = new StorageDAO();
        Product product = sd.getProductUPC("W001");
    }
}
