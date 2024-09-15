package DataAccessObject;

import DatabaseTools.ProductTools;
import DatabaseTools.SupplierTools;
import DatabaseTools.SupplierTools;
import DatabaseTools.SupplierTools;
import Driver.Utils;
import Entity.Supplier;
import Entity.Supplier;
import Entity.Supplier;

import java.util.ArrayList;
import java.util.Scanner;

public class SupplierDAO {
    //    private String supplierId;
    //    private String supplierName;
    //    private String supplierAddress;
    //    private String supplierPhone;
    //    private String supplierEmail;
    static Scanner scanner = new Scanner(System.in);

    public static void createSupplier(){
        Supplier supplier = new Supplier();

        //input name
        inputName(supplier);

        //input address
        inputAddress(supplier);

        //input phone
        inputPhone(supplier);

        //input email
        inputEmail(supplier);

        //generate id of the supplier
        supplier.setSupplierId(generateSupplierId());

        //insert supplier into database

        do{
            //confirm product information
            System.out.println("Supplier information:");
            System.out.println("1. Name: " + supplier.getSupplierName());
            System.out.println("2. Address: " + supplier.getSupplierAddress());
            System.out.println("3. Phone: " + supplier.getSupplierPhone());
            System.out.println("4. Email: " + supplier.getSupplierEmail());
            System.out.print("Are you sure the supplier information is correct? (y/n) : ");
            if (scanner.next().equalsIgnoreCase("y")) {
                SupplierTools.insertSupplier(supplier);
                break;
            } else {
                System.out.print("Select an option to modify: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        inputName(supplier);
                        break;
                    case 2:
                        inputAddress(supplier);
                        break;
                    case 3:
                        inputPhone(supplier);
                        break;
                    case 4:
                        inputEmail(supplier);
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            }
        }while(true);
    }

    public static void inputName(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier name: ");
        supplier.setSupplierName(scanner.nextLine());
    }

    public static void inputAddress(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier address: ");
        supplier.setSupplierAddress(scanner.nextLine());
    }

    public static void inputPhone(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier phone number: ");
        supplier.setSupplierPhone(scanner.nextLine());
    }

    public static void inputEmail(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier email: ");
        supplier.setSupplierEmail(scanner.nextLine());
    }

    public static String generateSupplierId(){
        String maxSupplierID = SupplierTools.retrieveMaxSupplierID().replace("S", "");
        return "SUP" + (Integer.parseInt(maxSupplierID) + 1);
    }

    //Read
    public static void displayAllSuppliers(){
        ArrayList<Supplier> suppliers = SupplierTools.retrieveAllSuppliers();
        if(suppliers.isEmpty()){
            System.out.println("No suppliers found!");
        }else{
            System.out.println("List of all suppliers:");
            System.out.println("-----");
            System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("----");

            for(Supplier supplier : suppliers){
                System.out.printf("%-10s|%-50s|%-50s|%-20s|%-50s\n", supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierPhone(), supplier.getSupplierEmail());
            }

            System.out.println("Press [enter] to continue...");
            scanner.nextLine();
        }
    }

    //Delete
    public static void deleteSupplier(){
        System.out.print("Please enter supplier ID to delete: ");
        String inputSupplierID = scanner.nextLine();
        if(SupplierTools.deleteSupplier(inputSupplierID)){
            System.out.println("Product deleted successfully");
        }else{
            System.out.println("Something went wrong!");
        }
    }

    //Update
    public static void updateSupplier(){
        int option = 999;

        System.out.print("Please enter supplier ID to update: ");
        String supplierID = scanner.nextLine();
        Supplier supplier = SupplierTools.retrieveSupplier(supplierID);
        if(supplier == null){
            System.out.println("Product not found!");
        }

        do{
            System.out.println("Supplier information:");
            System.out.println("1. Name: " + supplier.getSupplierName());
            System.out.println("2. Address: " + supplier.getSupplierAddress());
            System.out.println("3. Phone: " + supplier.getSupplierPhone());
            System.out.println("4. Email: " + supplier.getSupplierEmail());
            System.out.println();
            option = Utils.getIntInput("Select an option to modify or type 0 to exit: ");
            switch(option){
                case 0:
                    break;
                case 1:
                    inputName(supplier);
                    break;
                case 2:
                    inputAddress(supplier);
                    break;
                case 3:
                    inputPhone(supplier);
                    break;
                case 4:
                    inputEmail(supplier);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }while(option != 0);
    }

    //search
    public static void searchSupplier() {
        Scanner scanner = new Scanner(System.in);
        String searchField = "";
        String searchValue = "";

        System.out.println("Search Supplier By:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Address");
        System.out.println("4. Phone");
        System.out.println("5. Email");
        System.out.print("Select an option (1-5): ");

        int option = Utils.getIntInput("");

        switch (option) {
            case 1:
                searchField = "SupplierID";
                System.out.print("Enter Supplier ID: ");
                break;
            case 2:
                searchField = "SupplierName";
                System.out.print("Enter Supplier Name: ");
                break;
            case 3:
                searchField = "SupplierAddress";
                System.out.print("Enter Supplier Address: ");
                break;
            case 4:
                searchField = "SupplierPhone";
                System.out.print("Enter Supplier Phone: ");
                break;
            case 5:
                searchField = "SupplierEmail";
                System.out.print("Enter Supplier Email: ");
                break;
            default:
                System.out.println("Invalid option!");
                return;
        }

        searchValue = scanner.nextLine();

        ArrayList<Supplier> suppliers = SupplierTools.searchSuppliers(searchField, searchValue);

        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found!");
        } else {
            System.out.println("Search Results:");
            System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n", "ID", "Name", "Address", "Phone", "Email");
            System.out.println("-------------------------------------------------------------------------------------------");
            for (Supplier supplier : suppliers) {
                System.out.printf("%-10s | %-20s | %-30s | %-15s | %-25s\n",
                        supplier.getSupplierId(),
                        supplier.getSupplierName(),
                        supplier.getSupplierAddress(),
                        supplier.getSupplierPhone(),
                        supplier.getSupplierEmail());
            }

            System.out.println("Press [enter] to continue...");
            scanner.nextLine();
        }
    }
    
}
