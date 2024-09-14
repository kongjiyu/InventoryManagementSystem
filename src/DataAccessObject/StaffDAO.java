package DataAccessObject;

import DatabaseTools.LogTools;
import DatabaseTools.StaffTools;
import Entity.Staff;
import Entity.Validator;

import java.time.LocalDate;
import java.util.Scanner;

public class StaffDAO {

    public static void registerStaff() {
        Scanner sc = new Scanner(System.in);
        Staff newStaff = new Staff();

        System.out.print("Enter Staff ID: ");
        newStaff.setStaffID(sc.nextLine());

        if (StaffTools.checkUsername(newStaff.getUsername())) {
            System.out.println("Staff ID already exists! Please try another Staff ID!");
            return;
        }

        do {
            System.out.print("Enter Username: ");
            newStaff.setUsername(sc.nextLine());
            if (!Validator.validateUsername(newStaff.getUsername())) {
                System.out.println("Invalid Username Format! Please try again!");
                System.out.println("Username must contain at least one Uppercase and lowercase letters!");
            }
        } while (!Validator.validateUsername(newStaff.getUsername()));

        do {
            System.out.print("Enter Password: ");
            newStaff.setPassword(sc.nextLine());
            if (!Validator.validatePassword(newStaff.getPassword())) {
                System.out.println("Invalid Password");
                System.out.println("Password must contain at least one uppercase letter, one lowercase letter, one number, " +
                        "one symbol, and be between 8 and 12 characters long.");
            }
        } while (!Validator.validatePassword(newStaff.getPassword()));

        do {
            System.out.print("Enter Staff Malaysia IC: ");
            newStaff.setStaffIC(sc.nextLine());
            if (!Validator.validateIc(newStaff.getStaffIC())) {
                System.out.println("Invalid Ic");
                System.out.println("IC number should be between 12 digits");
            }
        } while (!Validator.validateIc(newStaff.getStaffIC()));

        System.out.print("Enter Name: ");
        newStaff.setName(sc.nextLine());

        System.out.print("Enter Age: ");
        newStaff.setAge(sc.nextInt());
        sc.nextLine();

        do {
            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            newStaff.setHireDate(LocalDate.parse(sc.nextLine()));
            if (!Validator.validateHireDate(newStaff.getHireDate().toString())) {
                System.out.println("Invalid Hire Date");
                System.out.println("Hire Date must be in the format YYYY-MM-DD");
            }
        } while (!Validator.validateHireDate(newStaff.getHireDate().toString()));

        do {
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            newStaff.setBirthDate(LocalDate.parse(sc.nextLine()));
            if (!Validator.validateDOB(newStaff.getBirthDate().toString())) {
                System.out.println("Invalid Date of Birth");
                System.out.println("Date of Birth must be in the format YYYY-MM-DD");
            }
        } while (!Validator.validateDOB(newStaff.getBirthDate().toString()));

        System.out.print("Enter Salary: ");
        newStaff.setSalary(sc.nextDouble());
        sc.nextLine();

        do {
            System.out.print("Enter Email: ");
            newStaff.setEmail(sc.nextLine());
            if (!Validator.validateEmail(newStaff.getEmail())) {
                System.out.println("Invalid Email");
                System.out.println("Email Must End With @xxxxx.com");
            }
        } while (!Validator.validateEmail(newStaff.getEmail()));

        System.out.print("Enter Phone Number: ");
        newStaff.setPhone(sc.nextLine());

        System.out.print("Enter Address: ");
        newStaff.setAddress(sc.nextLine());

        String isAdmin;
        do {
            System.out.println("Is this staff an Admin? (Y/N): ");
            isAdmin = sc.nextLine().toUpperCase();

            if (isAdmin.equals("Y")) {
                newStaff.setAdmin(true);
            } else if (isAdmin.equals("N")) {
                newStaff.setAdmin(false);
            } else {
                System.out.println("Invalid Input. Please enter 'Y' or 'N'");
            }
        } while (!isAdmin.equals("Y") && !isAdmin.equals("N"));

        if (StaffTools.registerStaff(newStaff)) {
            System.out.println("Staff Registered Successfully!");
        } else {
            System.out.println("Staff Registered Failed. Please try again!");
        }

    }

    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();

        System.out.print("Enter username: ");
        staff.setStaffID(scanner.nextLine());
        if (!StaffTools.checkUsername(staff.getUsername())) {
            System.out.println("Invalid username!");
            return false;
        }

        System.out.print("Enter password: ");
        staff.setPassword(scanner.nextLine());
        if (!StaffTools.checkPassword(staff.getUsername(), staff.getPassword())) {
            return false;
        }else{
            staff.setStaffID(StaffTools.getStaffID(staff.getUsername()));
            LogTools.insertLog(staff.getStaffID());


            if (StaffTools.checkAdmin(staff)) {
                adminMenu(staff.getUsername());
            } else {
                staffMenu(staff.getUsername());
            }
        }
        return true;
    }

    public static void adminMenu(String username) {
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
        } while (choice != 0);
    }

    public static void staffMenu(String username) {
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
        } while (choice != 0);
    }

}