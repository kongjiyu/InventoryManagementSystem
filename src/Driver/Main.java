package Driver;

import DataAccessObject.ProductDAO;
import DataAccessObject.StaffDAO;
import DataAccessObject.StorageDAO;
import DataAccessObject.TransferDAO;
import Database.DatabaseUtils;
import Entity.Product;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();
        TransferDAO tDAO = new TransferDAO();
        System.out.println(tDAO.transferStock("W001"));
    }
}
