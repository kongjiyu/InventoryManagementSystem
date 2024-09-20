package Driver;

import DataAccessObject.*;
import Database.DatabaseUtils;
import Entity.Product;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseUtils.getConnection();
        InventoryDAO inventoryDAO = new InventoryDAO();
        int choice;
        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Warehouse Management System");
            System.out.println("===========================");
            System.out.println("[1] Login");
            System.out.println("[2] Exit");
            System.out.println("===========================");
            choice = Utils.getIntInput("> ");
            switch (choice) {
                case 1:
                    StaffDAO.login();
                    break;
                case 2:
                    inventoryDAO.stockIn("W001", "S001");
                    break;
            }
        }while(choice!=2);

    }
}
