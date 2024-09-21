package DataAccessObject;

import DatabaseTools.RetailerTools;
import Driver.Utils;
import Entity.Retailer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RetailerDAO {
    //    private String retailerId;
    //    private String retailerName;
    //    private String retailerAddress;
    //    private String retailerPhone;
    //    private String retailerEmail;

    static Scanner scanner = new Scanner(System.in);

    //Create
    public static void createRetailer(){
        Retailer retailer = new Retailer();
        RetailerTools retailerTools = new RetailerTools();
        //input name
        inputName(retailer);

        //input address
        inputAddress(retailer);

        //input phone
        inputPhone(retailer);

        //input email
        inputEmail(retailer);

        //generate id of the retailer
        retailer.setRetailerId(retailerTools.getPrimaryKey());

        do{
            //confirm product information
            System.out.println("Retailer information:");
            System.out.println("1. Name: " + retailer.getRetailerName());
            System.out.println("2. Address: " + retailer.getRetailerAddress());
            System.out.println("3. Phone: " + retailer.getRetailerPhone());
            System.out.println("4. Email: " + retailer.getRetailerEmail());
            System.out.print("Are you sure the retailer information is correct? (y/n) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                RetailerTools.insertRetailer(retailer);
                break;
            } else if (choice.equalsIgnoreCase("n")) {
                int option = Utils.getIntInput("Select an option to modify: ");
                switch (option) {
                    case 1:
                        inputName(retailer);
                        break;
                    case 2:
                        inputAddress(retailer);
                        break;
                    case 3:
                        inputPhone(retailer);
                        break;
                    case 4:
                        inputEmail(retailer);
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
            }else {
                System.out.println("Invalid Input!");
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }while(true);
    }

    public static void inputName(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer name: ");
        retailer.setRetailerName(scanner.nextLine());
    }

    public static void inputAddress(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer address: ");
        retailer.setRetailerAddress(scanner.nextLine());
    }

    public static void inputPhone(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer phone number: ");
        retailer.setRetailerPhone(scanner.nextLine());
    }

    public static void inputEmail(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer email: ");
        retailer.setRetailerEmail(scanner.nextLine());
    }

    public static String generateRetailerId(){
        RetailerTools retailerTools = new RetailerTools();
        String maxRetailerID = retailerTools.getPrimaryKey().replace("R", "");
        return "R" + String.format("%03d", (Integer.parseInt(maxRetailerID) + 1));
    }

    //Read
    public static void displayAllRetailers() {
        ArrayList<Retailer> retailers = RetailerTools.retrieveAllRetailers();

        if (retailers.isEmpty()) {
            System.out.println("No retailers found!");
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            int totalRetailers = retailers.size();
            final int retailersPerPage = 5;  // Number of retailers to show per page
            int page = 0;
            int maxPages = (totalRetailers - 1) / retailersPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * retailersPerPage;
                int endIndex = Math.min(startIndex + retailersPerPage, totalRetailers);

                // Display the current page of retailers
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("List of all retailers:");
                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", "ID", "Name", "Address", "Phone", "Email");
                System.out.println("=========================================================================================================================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Retailer retailer = retailers.get(i);
                    System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", retailer.getRetailerId(), retailer.getRetailerName(), retailer.getRetailerAddress(), retailer.getRetailerPhone(), retailer.getRetailerEmail());
                    count++;
                }

                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total retailers: %d\n", totalRetailers);
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
    public static void deleteRetailer(){
        System.out.print("Please enter retailer ID to delete: ");
        String inputRetailerID = scanner.nextLine();
        if(RetailerTools.deleteRetailer(inputRetailerID)){
            System.out.println("Retailer deleted successfully");
        }else{
            System.out.println("Retailer not found!\n");
        }
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //Update
    public static void updateRetailer(){
        int option = 999;

        System.out.print("Please enter retailer ID to update: ");
        String retailerID = scanner.nextLine();
        Retailer retailer = RetailerTools.retrieveRetailer(retailerID);
        if(retailer == null){
            return;
        }

        do{
            System.out.println("Retailer information:");
            System.out.println("[1] Name: " + retailer.getRetailerName());
            System.out.println("[2] Address: " + retailer.getRetailerAddress());
            System.out.println("[3] Phone: " + retailer.getRetailerPhone());
            System.out.println("[4] Email: " + retailer.getRetailerEmail());
            System.out.println();
            option = Utils.getIntInput("Select an option to modify or type 0 to exit: ");
            switch(option){
                case 0:
                    break;
                case 1:
                    inputName(retailer);
                    break;
                case 2:
                    inputAddress(retailer);
                    break;
                case 3:
                    inputPhone(retailer);
                    break;
                case 4:
                    inputEmail(retailer);
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
        }while(option != 0);
    }

    public static void searchRetailer() {
        Scanner scanner = new Scanner(System.in);
        String searchField = "";
        String searchValue = "";

        System.out.println("Search Retailer By:");
        System.out.println("[1] ID");
        System.out.println("[2] Name");
        System.out.println("[3] Address");
        System.out.println("[4] Phone");
        System.out.println("[5] Email");
        int option = Utils.getIntInput("Select an option (1-5): ");

        switch (option) {
            case 1:
                searchField = "RetailerID";
                System.out.print("Enter Retailer ID: ");
                break;
            case 2:
                searchField = "RetailerName";
                System.out.print("Enter Retailer Name: ");
                break;
            case 3:
                searchField = "RetailerAddress";
                System.out.print("Enter Retailer Address: ");
                break;
            case 4:
                searchField = "RetailerPhone";
                System.out.print("Enter Retailer Phone: ");
                break;
            case 5:
                searchField = "RetailerEmail";
                System.out.print("Enter Retailer Email: ");
                break;
            default:
                System.out.println("Invalid option!");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                return;
        }

        searchValue = scanner.nextLine();

        ArrayList<Retailer> retailers = RetailerTools.searchRetailers(searchField, searchValue);

        if (retailers.isEmpty()) {
            System.out.println("No retailers found!");
        } else {
            int totalRetailers = retailers.size();
            final int retailersPerPage = 5;  // Number of retailers to show per page
            int page = 0;
            int maxPages = (totalRetailers - 1) / retailersPerPage;
            boolean exit = false;

            do {
                int count = 1;
                int startIndex = page * retailersPerPage;
                int endIndex = Math.min(startIndex + retailersPerPage, totalRetailers);

                // Display the current page of retailers
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Search Results:");
                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", "ID", "Name", "Address", "Phone", "Email");
                System.out.println("=========================================================================================================================================================================================");

                for (int i = startIndex; i < endIndex; i++) {
                    Retailer retailer = retailers.get(i);
                    System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s|\n", retailer.getRetailerId(), retailer.getRetailerName(), retailer.getRetailerAddress(), retailer.getRetailerPhone(), retailer.getRetailerEmail());
                    count++;
                }

                System.out.println("=========================================================================================================================================================================================");
                System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
                System.out.printf("Total retailers: %d\n", totalRetailers);
                System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
                System.out.print("Select navigation option: ");

                String input = scanner.nextLine().trim();

                if (input.length() == 1) {
                    char choice = input.charAt(0);

                    switch (choice) {
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

    public static void retailerMenu(){
        int option = 999;


        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Manage Retailer");
            System.out.println("========================");
            System.out.println("[1] Display All Retailer");
            System.out.println("[2] Search Retailer");
            System.out.println("[3] Delete Retailer");
            System.out.println("[4] Update Retailer");
            System.out.println("[5] Create New Retailer");
            System.out.println("[6] Exit");
            System.out.println("========================");
            option = Utils.getIntInput("Please select an option: ");
            switch(option){
                case 1:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    displayAllRetailers();
                    break;
                case 2:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    searchRetailer();
                    break;
                case 3:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    deleteRetailer();
                    break;
                case 4:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    updateRetailer();
                    break;
                case 5:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    createRetailer();
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

    public Retailer getRetailer(){
        // Create objects for further use
        Scanner scanner = new Scanner(System.in);
        RetailerTools rt = new RetailerTools();
        Retailer retailer = null;
        int choice = 0;

        // Let user choose how to search for retailer
        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("[1] Search Retailer by ID");
            System.out.println("[2] Search Retailer by Name");
            System.out.println("[3] List All Retailers");
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
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                continue;
            }

            switch(choice) {
                // Find retailer by ID
                case 1:
                    System.out.print("Enter Retailer ID: ");
                    String id = scanner.nextLine();
                    retailer = rt.getRetailerById(id);
                    break;
                // Find retailer by Name
                case 2:
                    System.out.print("Enter Retailer Name: ");
                    String name = scanner.nextLine();
                    retailer = rt.getRetailerByName(name);
                    break;
                // List all retailers
                case 3:
                    retailer = rt.getRetailerByList();
                    break;
                // Exit loop
                case 999:
                    return null;
                // Invalid input
                default:
                    System.out.println("Invalid input!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
            }

            // Confirm retailer selection
            if (retailer != null) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Retailer Detail Confirmation");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-30s|\n", "Retailer ID", "Retailer Name", "Retailer Address", "Retailer Phone", "Retailer Email");
                System.out.println("===============================================================================================================================================================================================");
                System.out.printf("|%-15s|%-20s|%-100s|%-20s|%-30s|\n",
                        retailer.getRetailerId(),
                        retailer.getRetailerName(),
                        retailer.getRetailerAddress(),
                        retailer.getRetailerPhone(),
                        retailer.getRetailerEmail());
                System.out.println("===============================================================================================================================================================================================");

                String confirmation;
                do {
                    System.out.print("Confirm this retailer? <Y/N> : ");
                    confirmation = scanner.nextLine().trim().toUpperCase();

                    if (confirmation.equals("N")) {
                        retailer = null;
                        break;
                    } else if (!confirmation.equals("Y")) {
                        System.out.println("Invalid input! Please enter Y or N.");
                    }
                } while (!confirmation.equals("Y") && !confirmation.equals("N"));
            }
        } while (retailer == null);

        // Return the selected retailer
        return retailer;
    }
}
