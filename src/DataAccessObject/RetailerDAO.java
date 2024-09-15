package DataAccessObject;

import DatabaseTools.RetailerTools;
import Driver.Utils;
import Entity.Retailer;

import java.util.ArrayList;
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

        //input name
        inputName(retailer);

        //input address
        inputAddress(retailer);

        //input phone
        inputPhone(retailer);

        //input email
        inputEmail(retailer);

        //generate id of the retailer
        retailer.setRetailerId(generateRetailerId());

        do{
            //confirm product information
            System.out.println("Retailer information:");
            System.out.println("1. Name: " + retailer.getRetailerName());
            System.out.println("2. Address: " + retailer.getRetailerAddress());
            System.out.println("3. Phone: " + retailer.getRetailerPhone());
            System.out.println("4. Email: " + retailer.getRetailerEmail());
            System.out.print("Are you sure the retailer information is correct? (y/n) : ");
            if (scanner.next().equalsIgnoreCase("y")) {
                RetailerTools.insertRetailer(retailer);
                break;
            } else {
                System.out.print("Select an option to modify: ");
                int option = scanner.nextInt();
                scanner.nextLine();
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
                        break;
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
        String maxRetailerID = RetailerTools.retrieveMaxRetailerID().replace("S", "");
        return "RET" + (Integer.parseInt(maxRetailerID) + 1);
    }

    //Read
    public static void displayAllRetailers(){
        ArrayList<Retailer> retailers = RetailerTools.retrieveAllRetailers();
        if(retailers.isEmpty()){
            System.out.println("No retailers found!");
        }else{
            System.out.println("List of all retailers:");
            System.out.println("-----");
            System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("----");

            for(Retailer retailer : retailers){
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", retailer.getRetailerId(), retailer.getRetailerName(), retailer.getRetailerAddress(), retailer.getRetailerPhone(), retailer.getRetailerEmail());
            }

            System.out.println("Press [enter] to continue...");
            scanner.nextLine();
        }
    }

    //Delete
    public static void deleteRetailer(){
        System.out.print("Please enter retailer ID to delete: ");
        String inputRetailerID = scanner.nextLine();
        if(RetailerTools.deleteRetailer(inputRetailerID)){
            System.out.println("Product deleted successfully");
        }else{
            System.out.println("Something went wrong!");
        }
    }

    //Update
    public static void updateRetailer(){
        int option = 999;

        System.out.print("Please enter retailer ID to update: ");
        String retailerID = scanner.nextLine();
        Retailer retailer = RetailerTools.retrieveRetailer(retailerID);
        if(retailer == null){
            System.out.println("Product not found!");
        }

        do{
            System.out.println("Retailer information:");
            System.out.println("1. Name: " + retailer.getRetailerName());
            System.out.println("2. Address: " + retailer.getRetailerAddress());
            System.out.println("3. Phone: " + retailer.getRetailerPhone());
            System.out.println("4. Email: " + retailer.getRetailerEmail());
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
                    break;
            }
        }while(option != 0);
    }

    public static void searchRetailer() {
        Scanner scanner = new Scanner(System.in);
        String searchField = "";
        String searchValue = "";

        System.out.println("Search Retailer By:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Address");
        System.out.println("4. Phone");
        System.out.println("5. Email");
        System.out.print("Select an option (1-5): ");

        int option = Utils.getIntInput("");

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
                return;
        }

        searchValue = scanner.nextLine();

        ArrayList<Retailer> retailers = RetailerTools.searchRetailers(searchField, searchValue);

        if (retailers.isEmpty()) {
            System.out.println("No retailers found!");
        } else {
            System.out.println("Search Results:");
            System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("-------------------------------------------------------------------------------------------");
            for (Retailer retailer : retailers) {
                System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n",
                        retailer.getRetailerId(),
                        retailer.getRetailerName(),
                        retailer.getRetailerAddress(),
                        retailer.getRetailerPhone(),
                        retailer.getRetailerEmail());
            }
        }
    }

    public static void retailerMenu(){
        int option = 999;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

        do{
            System.out.println("Manage Retailer");
            System.out.println("1. Display All Retailer");
            System.out.println("2. Search Retailer");
            System.out.println("3. Delete Retailer");
            System.out.println("4. Update Retailer");
            System.out.println("5. Create New Retailer");
            System.out.println("6. Exit");

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
                    break;
            }
        }while(option != 6);

    }
}
