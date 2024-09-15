package DataAccessObject;

import DatabaseTools.ProductTools;
import DatabaseTools.SupplierTools;
import Entity.Supplier;

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
        String supplierName = scanner.nextLine();
    }

    public static void inputAddress(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier address: ");
        String supplierAddress = scanner.nextLine();
    }

    public static void inputPhone(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier phone number: ");
        String supplierPhoneNumber = scanner.nextLine();
    }

    public static void inputEmail(Supplier supplier){
        System.out.println();
        System.out.print("Please enter supplier email: ");
        String supplierEmail = scanner.nextLine();
    }

    public static String generateSupplierId(){
        String maxSupplierID = SupplierTools.retrieveMaxSupplierID().replace("S", "");
        return "SUP" + (Integer.parseInt(maxSupplierID) + 1);
    }

}
