package DataAccessObject;

import DatabaseTools.WarehouseTools;
import Driver.Utils;
import Entity.Warehouse;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println("[1] Name: " + warehouse.getWarehouseName());
            System.out.println("[2] Address: " + warehouse.getWarehouseAddress());
            System.out.println("[3] Phone: " + warehouse.getWarehousePhone());
            System.out.println("[4] Email: " + warehouse.getWarehouseEmail());
            System.out.print("Are you sure the warehouse information is correct? (y/n) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                WarehouseTools.insertWarehouse(warehouse);
                break;
            } else if (choice.equalsIgnoreCase("n")) {
                int option = Utils.getIntInput("Select an option to modify: ");
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
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                }
            }else {
                System.out.println("Invalid Input!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }while(true);
    }

    public static void inputName(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse name: ");
        warehouse.setWarehouseName(scanner.nextLine());
    }

    public static void inputAddress(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse address: ");
        warehouse.setWarehouseAddress(scanner.nextLine());
    }

    public static void inputPhone(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse phone number: ");
        warehouse.setWarehousePhone(scanner.nextLine());
    }

    public static void inputEmail(Warehouse warehouse){
        System.out.println();
        System.out.print("Please enter warehouse email: ");
        warehouse.setWarehouseEmail(scanner.nextLine());
    }

    public static String generateWarehouseId(){
        WarehouseTools warehouseTools = new WarehouseTools();
        String maxWarehouseID = warehouseTools.getPrimaryKey().replace("W", "");
        return ("W" + String.format("%03d", (Integer.parseInt(maxWarehouseID) + 1)));
    }

    //Read
    public static void displayAllWarehouses(){
        ArrayList<Warehouse> warehouses = WarehouseTools.retrieveAllWarehouses();

        if (warehouses.isEmpty()) {
            System.out.println("No warehouses found!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalWarehouses = warehouses.size();
            final int warehousesPerPage = 5;  // Number of warehouses to show per page
            int page = 0;
            int maxPages = (totalWarehouses - 1) / warehousesPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * warehousesPerPage;
                int endIndex = Math.min(startIndex + warehousesPerPage, totalWarehouses);

                // Display the current page of warehouses
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("List of all warehouses:");
                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", "ID", "Name", "Address", "Phone", "Email");
                System.out.println("=========================================================================================================================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Warehouse warehouse = warehouses.get(i);
                    System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", warehouse.getWarehouseId(), warehouse.getWarehouseName(), warehouse.getWarehouseAddress(), warehouse.getWarehousePhone(), warehouse.getWarehouseEmail());
                    count++;
                }

                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total warehouses: %d\n", totalWarehouses);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char option = input.charAt(0);

                    switch (option) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input. Please enter 'A', 'D', or 'Q'.");
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } while (!exit);
        }

    }

    //Delete
    public static void deleteWarehouse(){
        System.out.print("Please enter warehouse ID to delete: ");
        String inputWarehouseID = scanner.nextLine();
        if(WarehouseTools.deleteWarehouse(inputWarehouseID)){
            System.out.println("Product deleted successfully");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Warehouse Not found!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //Update
    public static void updateWarehouse(){
        int option = 999;

        System.out.print("Please enter warehouse ID to update: ");
        String warehouseID = scanner.nextLine();
        Warehouse warehouse = WarehouseTools.retrieveWarehouse(warehouseID);
        if(warehouse == null){
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return;
        }

        do{
            System.out.println("Warehouse information:");
            System.out.println("[1] Name: " + warehouse.getWarehouseName());
            System.out.println("[2] Address: " + warehouse.getWarehouseAddress());
            System.out.println("[3] Phone: " + warehouse.getWarehousePhone());
            System.out.println("[4] Email: " + warehouse.getWarehouseEmail());
            System.out.println();
            option = Utils.getIntInput("Select an option to modify or type 0 to exit: ");
            switch(option){
                case 0:
                    WarehouseTools.updateWarehouse(warehouse);
                    System.out.println("Update successfull!");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

            }
        }while(option != 0);
    }

    //Search
    public static void searchWarehouse() {
        Scanner scanner = new Scanner(System.in);
        String searchField = "";
        String searchValue = "";

        System.out.println("Search Warehouse By:");
        System.out.println("[1] ID");
        System.out.println("[2] Name");
        System.out.println("[3] Address");
        System.out.println("[4] Phone");
        System.out.println("[5] Email");

        int option = Utils.getIntInput("Select an option (1-5): ");

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
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
        }

        searchValue = scanner.nextLine();

        ArrayList<Warehouse> warehouses = WarehouseTools.searchWarehouses(searchField, searchValue);

        if (warehouses.isEmpty()) {
            System.out.println("No warehouses found!");
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            int totalWarehouses = warehouses.size();
            final int warehousesPerPage = 5;  // Number of warehouses to show per page
            int page = 0;
            int maxPages = (totalWarehouses - 1) / warehousesPerPage;
            boolean exit = false;

            do {
                int startIndex = page * warehousesPerPage;
                int endIndex = Math.min(startIndex + warehousesPerPage, totalWarehouses);

                // Display the current page of search results
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Search Results:");
                System.out.println("==================================================================================================================================================================================================");
                System.out.printf("%-10s | %-50s | %-50s | %-20s | %-50s |\n", "ID", "Name", "Address", "Phone", "Email");
                System.out.println("==================================================================================================================================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Warehouse warehouse = warehouses.get(i);
                    System.out.printf("%-10s | %-50s | %-50s | %-20s | %-50s |\n",
                            warehouse.getWarehouseId(),
                            warehouse.getWarehouseName(),
                            warehouse.getWarehouseAddress(),
                            warehouse.getWarehousePhone(),
                            warehouse.getWarehouseEmail());
                }

                System.out.println("==================================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total warehouses: %d\n", totalWarehouses);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char userInput = input.charAt(0);

                    switch (userInput) {
                        case 'A':
                        case 'a':
                            if (page > 0) page--;
                            break;
                        case 'D':
                        case 'd':
                            if (page < maxPages) page++;
                            break;
                        case 'Q':
                        case 'q':
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input. Please enter 'A', 'D', or 'Q'.");
                            try{
                                Thread.sleep(500);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a single character.");
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } while (!exit);
        }
    }

    public Warehouse getWarehouseToTransfer(){
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
                    Thread.sleep(500);
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
                        Thread.sleep(500);
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

    public static void warehouseMenu(){
        int option = 999;

        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Manage Warehouse");
            System.out.println("=========================");
            System.out.println("[1] Display All Warehouse");
            System.out.println("[2] Search Warehouse");
            System.out.println("[3] Delete Warehouse");
            System.out.println("[4] Update Warehouse");
            System.out.println("[5] Create New Warehouse");
            System.out.println("[6] Exit");
            System.out.println("=========================");

            option = Utils.getIntInput("Please select an option: ");
            switch(option){
                case 1:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    displayAllWarehouses();
                    break;
                case 2:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    searchWarehouse();
                    break;
                case 3:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    deleteWarehouse();
                    break;
                case 4:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    updateWarehouse();
                    break;
                case 5:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    createWarehouse();
                    break;
                case 6:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    break;
                default:
                    System.out.println("Invalid option!");
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }while(option != 6);

    }
}
