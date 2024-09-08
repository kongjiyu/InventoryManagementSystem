package Driver;

import DataAccessObject.ProductDAO;
import DataAccessObject.StaffDAO;
import DataAccessObject.SupplierDAO;
import Database.DatabaseUtils;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ProductDAO.createProduct();
    }
}
