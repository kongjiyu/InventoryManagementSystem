package DataAccessObject;

import DatabaseTools.StaffTools;
import Entity.Staff;

import java.util.Scanner;

public class StaffDAO {
    public static boolean checkStaffAccount() {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();

        System.out.print("Enter username: ");
        staff.setStaffID(scanner.nextLine());
        System.out.print("Enter password: ");
        staff.setPassword(scanner.nextLine());


        if (!StaffTools.checkStaffID(staff.getStaffID())) {
            System.out.print("Invalid username or password");
            return false;
        }


        if (!StaffTools.checkPassword(staff.getStaffID(), staff.getPassword())) {
            return false;
        }


        if (StaffTools.checkAdmin(staff)) {
            adminMenu();
        } else {
            StaffMenu();
        }

        return true;

    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();
        int choice;
        do {

            System.out.println("1.Manage Retailer");
            System.out.println("2.Manage Warehouse");
            System.out.println("3.Manage Account");
            System.out.println("4.Manage Product");
            System.out.println("5.Stock In");
            System.out.println("6.Stock Transfer");
            System.out.println("7.Stock Distribution");
            System.out.println("8.Stock Request");
            System.out.println("9.Check report");
            System.out.println("10.Check user Log");
            System.out.println("11.Staff Request Log");
            System.out.println("12.Exit");
            System.out.print("Select your option:");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    return;
                default:
                    System.out.print("Please try again for select");
                    break;
            }
        } while (choice != 0 );
    }

    public static void StaffMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1.Stock In");
            System.out.println("2.Stock Transfer");
            System.out.println("3.Stock Distribution");
            System.out.println("4.Stock Request");
            System.out.println("5.Exit");
            System.out.print("Select your option:");
            choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.print("Please try again for select");
                    break;
            }
        } while (choice !=0 );
    }

}