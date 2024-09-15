package DataAccessObject;

import DatabaseTools.WarehouseTools;
import Driver.Utils;
import Entity.Warehouse;

import java.util.ArrayList;
import java.util.Scanner;

public class WarehouseDAO {
    //    private String warehouseId;
    //    private String warehouseName;
    //    private String warehouseAddress;
    //    private String warehousePhone;
    //    private String warehouseEmail;

    static Scanner scanner = new Scanner(System.in);

    //Create
    public static void createWarehouse(){
        Warehouse warehouse = new Warehouse();

        //input name
        inputName(warehouse);

        //input address
        inputAddress(warehouse);

        //input phone
        inputPhone(warehouse);

        //input email
        inputEmail(warehouse);

        //generate id of the warehouse
        warehouse.setWarehouseId(generateWarehouseId());

        do{
            //confirm product information
            System.out.println("Warehouse information:");
            System.out.println("1. Name: " + warehouse.getWarehouseName());
            System.out.println("2. Address: " + warehouse.getWarehouseAddress());
            System.out.println("3. Phone: " + warehouse.getWarehousePhone());
            System.out.println("4. Email: " + warehouse.getWarehouseEmail());
            System.out.print("Are you sure the warehouse information is correct? (y/n) : ");
            if (scanner.next().equalsIgnoreCase("y")) {
                WarehouseTools.insertWarehouse(warehouse);
                break;
            } else {
                System.out.print("Select an option to modify: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        inputName(warehouse);
                        break;
                    case 2:
                        inputAddress(warehouse);
                        break;
                    case 3:
                        inputPhone(warehouse);
                        break;
                    case 4:
                        inputEmail(warehouse);
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            }
        }while(true);
    }

    public static void inputName(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse name: ");
        String warehouseName = scanner.nextLine();
    }

    public static void inputAddress(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse address: ");
        String warehouseAddress = scanner.nextLine();
    }

    public static void inputPhone(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse phone number: ");
        String warehousePhoneNumber = scanner.nextLine();
    }

    public static void inputEmail(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse email: ");
        String warehouseEmail = scanner.nextLine();
    }

    public static String generateWarehouseId(){
        String maxWarehouseID = WarehouseTools.retrieveMaxWarehouseID().replace("S", "");
        return "W" + (Integer.parseInt(maxWarehouseID) + 1);
    }

    //Read
    public static void displayAllWarehouses(){
        ArrayList<Warehouse> warehouses = WarehouseTools.retrieveAllWarehouses();
        if(warehouses.isEmpty()){
            System.out.println("No warehouses found!");
        }else{
            System.out.println("List of all warehouses:");
            System.out.println("-----");
            System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("----");

            for(Warehouse warehouse : warehouses){
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", warehouse.getWarehouseId(), warehouse.getWarehouseName(), warehouse.getWarehouseAddress(), warehouse.getWarehousePhone(), warehouse.getWarehouseEmail());
            }

            System.out.println("Press [enter] to continue...");
            scanner.nextLine();
        }
    }

    //Delete
    public static void deleteWarehouse(){
        System.out.print("Please enter warehouse ID to delete: ");
        String inputWarehouseID = scanner.nextLine();
        if(WarehouseTools.deleteWarehouse(inputWarehouseID)){
            System.out.println("Product deleted successfully");
        }else{
            System.out.println("Something went wrong!");
        }
    }

    //Update
    public static void updateWarehouse(){
        int option = 999;

        System.out.print("Please enter warehouse ID to update: ");
        String warehouseID = scanner.nextLine();
        Warehouse warehouse = WarehouseTools.retrieveWarehouse(warehouseID);
        if(warehouse == null){
            System.out.println("Product not found!");
        }

        do{
            System.out.println("Warehouse information:");
            System.out.println("1. Name: " + warehouse.getWarehouseName());
            System.out.println("2. Address: " + warehouse.getWarehouseAddress());
            System.out.println("3. Phone: " + warehouse.getWarehousePhone());
            System.out.println("4. Email: " + warehouse.getWarehouseEmail());
            System.out.println();
            option = Utils.getIntInput("Select an option to modify or type 0 to exit: ");
            switch(option){
                case 0:
                    break;
                case 1:
                    inputName(warehouse);
                    break;
                case 2:
                    inputAddress(warehouse);
                    break;
                case 3:
                    inputPhone(warehouse);
                    break;
                case 4:
                    inputEmail(warehouse);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }while(option != 0);
    }

    //Search
    public static void searchWarehouse() {
        Scanner scanner = new Scanner(System.in);
        String searchField = "";
        String searchValue = "";

        System.out.println("Search Warehouse By:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Address");
        System.out.println("4. Phone");
        System.out.println("5. Email");
        System.out.print("Select an option (1-5): ");

        int option = Utils.getIntInput("");

        switch (option) {
            case 1:
                searchField = "WarehouseID";
                System.out.print("Enter Warehouse ID: ");
                break;
            case 2:
                searchField = "WarehouseName";
                System.out.print("Enter Warehouse Name: ");
                break;
            case 3:
                searchField = "WarehouseAddress";
                System.out.print("Enter Warehouse Address: ");
                break;
            case 4:
                searchField = "WarehousePhone";
                System.out.print("Enter Warehouse Phone: ");
                break;
            case 5:
                searchField = "WarehouseEmail";
                System.out.print("Enter Warehouse Email: ");
                break;
            default:
                System.out.println("Invalid option!");
                return;
        }

        searchValue = scanner.nextLine();

        ArrayList<Warehouse> warehouses = WarehouseTools.searchWarehouses(searchField, searchValue);

        if (warehouses.isEmpty()) {
            System.out.println("No warehouses found!");
        } else {
            System.out.println("Search Results:");
            System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("-------------------------------------------------------------------------------------------");
            for (Warehouse warehouse : warehouses) {
                System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n",
                        warehouse.getWarehouseId(),
                        warehouse.getWarehouseName(),
                        warehouse.getWarehouseAddress(),
                        warehouse.getWarehousePhone(),
                        warehouse.getWarehouseEmail());
            }

            System.out.println("Press [enter] to continue...");
            scanner.nextLine();
        }
    }

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
