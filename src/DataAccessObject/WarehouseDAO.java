package DataAccessObject;

import DatabaseTools.WarehouseTools;
import Entity.Warehouse;

import java.util.Scanner;

public class WarehouseDAO {
    public Warehouse getWarehouse(){
        // create the object for further uses
        Scanner scanner = new Scanner(System.in);
        WarehouseTools wt = new WarehouseTools();
        Warehouse warehouse = null;

        // let user to choose how to search warehouse
        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("[1] Search Warehouse by ID");
            System.out.println("[2] Search Warehouse by Name");
            System.out.println("[999] Exit");
            System.out.print(" >");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice)
            {
                // find warehouse by ID
                case 1:
                    System.out.print("Enter Warehouse ID: ");
                    String id = scanner.nextLine();

                    warehouse = wt.getWarehouseById(id);
                    break;
                // find warehouse by Name
                case 2:
                    System.out.print("Enter Warehouse Name: ");
                    String name = scanner.nextLine();

                    warehouse = wt.getWarehouseByName(name);
                    break;
                // exit loop
                case 999:
                    return null;
                // invalid input
                default:
                    System.out.println("Invalid input!");
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
            }
            // check user choice is correct or not
            if (warehouse != null) {
                System.out.println("Product Detail Confirmation");
                System.out.println("=====================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-20s|\n", "Warehouse ID", "Warehouse Name", "Warehouse Address", "Warehouse Phone", "Warehouse Email");
                System.out.println("=====================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-20s|\n", warehouse.getWarehouseId(), warehouse.getWarehouseName(), warehouse.getWarehouseAddress(), warehouse.getWarehousePhone(), warehouse.getWarehouseEmail());
                System.out.println("=====================================================================================================================================================================================");
                System.out.println("Confirm this warehouse? <Y/N> :");
                if (scanner.nextLine().toUpperCase().equals("N")) {
                    warehouse = null;
                }
            }

        }while (warehouse == null);
        // return the data
        return warehouse;
    }
}
