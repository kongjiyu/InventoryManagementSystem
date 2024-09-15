package DataAccessObject;

import DatabaseTools.WarehouseTools;
import Entity.Warehouse;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WarehouseDAO {
    public Warehouse getWarehouse(){
        // create the object for further uses
        Scanner scanner = new Scanner(System.in);
        WarehouseTools wt = new WarehouseTools();
        Warehouse warehouse = null;
        int choice = 0;
        // let user to choose how to search warehouse
        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("[1] Search Warehouse by ID");
            System.out.println("[2] Search Warehouse by Name");
            System.out.println("[3] Search Warehouse by List");
            System.out.println("[999] Exit");
            System.out.print(" > ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                scanner.nextLine();
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                continue;
            }


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
                // find warehouse by list
                case 3:
                    warehouse = wt.getWarehouseByList();
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
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Warehouse Detail Confirmation");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "Warehouse ID", "Warehouse Name", "Warehouse Address", "Warehouse Phone", "Warehouse Email");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-30s|\n", warehouse.getWarehouseId(), warehouse.getWarehouseName(), warehouse.getWarehouseAddress(), warehouse.getWarehousePhone(), warehouse.getWarehouseEmail());
                System.out.println("===============================================================================================================================================================================================");

                String confirmation;
                do {
                    System.out.print("Confirm this warehouse? <Y/N> : ");
                    confirmation = scanner.nextLine().trim().toUpperCase();

                    // If input is "N", clear the product and return to loop
                    if (confirmation.equals("N")) {
                        warehouse = null;
                        break;
                    } else if (!confirmation.equals("Y")) {
                        // If input is neither "Y" nor "N", show invalid input message
                        System.out.println("Invalid input! Please enter Y or N.");
                    }
                }while (!confirmation.equals("Y") && !confirmation.equals("N"));
            }
        }while (warehouse == null);
        // return the data
        return warehouse;
    }
}
