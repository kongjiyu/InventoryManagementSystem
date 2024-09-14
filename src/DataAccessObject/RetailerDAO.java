package DataAccessObject;

import DatabaseTools.RetailerTools;
import Entity.Retailer;

import java.util.Scanner;

public class RetailerDAO {
    //    private String retailerId;
    //    private String retailerName;
    //    private String retailerAddress;
    //    private String retailerPhone;
    //    private String retailerEmail;

    static Scanner scanner = new Scanner(System.in);

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
        String retailerName = scanner.nextLine();
    }

    public static void inputAddress(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer address: ");
        String retailerAddress = scanner.nextLine();
    }

    public static void inputPhone(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer phone number: ");
        String retailerPhoneNumber = scanner.nextLine();
    }

    public static void inputEmail(Retailer retailer){
        System.out.println();
        System.out.print("Please enter retailer email: ");
        String retailerEmail = scanner.nextLine();
    }

    public static String generateRetailerId(){
        String maxRetailerID = RetailerTools.retrieveMaxRetailerID().replace("S", "");
        return "S" + (Integer.parseInt(maxRetailerID) + 1);
    }
}
