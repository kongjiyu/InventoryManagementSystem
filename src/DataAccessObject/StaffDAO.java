package DataAccessObject;


import DatabaseTools.LogTools;
import DatabaseTools.StaffTools;
import DatabaseTools.WarehouseTools;
import Driver.Utils;
import Entity.Admin;
import Entity.Staff;
import Entity.Validator;
import Entity.Warehouse;
import DataAccessObject.WarehouseDAO;
import Model.TransferSet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static Driver.Utils.getIntInput;

public class StaffDAO {
    public static void registerStaff() {
        Scanner sc = new Scanner(System.in);
        Staff newStaff = new Staff();
        WarehouseDAO wd = new WarehouseDAO();
        System.out.print("Enter Staff ID: ");
        newStaff.setStaffID(sc.nextLine());


        if (StaffTools.checkID(newStaff.getStaffID())) {
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
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.print("Enter Password: ");
            newStaff.setPassword(sc.nextLine());
            if (!Validator.validatePassword(newStaff.getPassword())) {
                System.out.println("Invalid Password");
                System.out.println("Password must contain at least one uppercase letter, one lowercase letter, one number, " +
                        "one symbol, and be between 8 and 12 characters long.");
            }
        } while (!Validator.validatePassword(newStaff.getPassword()));

        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.print("Enter Staff Malaysia IC: ");
            newStaff.setStaffIC(sc.nextLine());

            if(!Validator.validateIc(newStaff.getStaffIC())) {
                System.out.println("Invalid Ic");
                System.out.println("IC number should be between 12 digits");
            }
        } while (!Validator.validateIc(newStaff.getStaffIC()));

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Enter Name: ");
        newStaff.setName(sc.nextLine());

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        newStaff.setAge(getIntInput("Enter age: "));

        boolean check = false;
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        do {

            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            try {
                newStaff.setHireDate(LocalDate.parse(sc.nextLine()));
                if (!Validator.validateHireDate(newStaff.getHireDate().toString())) {
                    System.out.println("Invalid Hire Date");
                    System.out.println("Hire Date must be in the format YYYY-MM-DD");
                }else {
                    check = true;
                }
            }catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter the date in the format YYYY-MM-DD.");
            }
        } while (!check);
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        do {

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            try {
                newStaff.setBirthDate(LocalDate.parse(sc.nextLine()));
                if (!Validator.validateDOB(newStaff.getBirthDate().toString())) {
                    System.out.println("Invalid Date of Birth");
                    System.out.println("Date of Birth must be in the format YYYY-MM-DD");
                }else {
                    check = true;
                }
            }catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter the date in the format YYYY-MM-DD.");
            }
        } while (!check);

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        newStaff.setSalary(Utils.getDoubleInput("Enter Salaray: "));
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        do {

            System.out.print("Enter Email: ");
            newStaff.setEmail(sc.nextLine());
            if (!Validator.validateEmail(newStaff.getEmail())) {
                System.out.println("Invalid Email");
                System.out.println("Email Must End With @xxxxx.com");
            }
        } while (!Validator.validateEmail(newStaff.getEmail()));

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Enter Phone Number: ");
        newStaff.setPhone(sc.nextLine());

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Enter Address: ");
        newStaff.setAddress(sc.nextLine());

        String adminPrivilege;
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        do {

            System.out.println("Is this staff an Admin? (Y/N): ");
            adminPrivilege = sc.nextLine().toUpperCase();
            if (adminPrivilege.equals("Y")) {
                newStaff.setAdmin(true);
                String adminType;
                do {

                    System.out.println("[0] Super Admin (Full access to all functions)");
                    System.out.println("[1] HR Admin (Manage Account, User Log)");
                    System.out.println("[2] Warehouse Manager (Manage Warehouse, Stock Management, Check Request Log)");
                    System.out.println("[3] Product Manager (Manage Product)");
                    System.out.println("[4] Normal Staff (No admin privileges)");
                    System.out.print(" > ");

                    adminType = sc.nextLine();

                    switch (adminType) {
                        case "0":
                            newStaff = new Admin(newStaff.getStaffID(), newStaff.getUsername(), newStaff.getPassword(), newStaff.getStaffIC(), newStaff.getName(), newStaff.getAge(), newStaff.getHireDate(), newStaff.getBirthDate(), newStaff.getSalary(), newStaff.getEmail(), newStaff.getPhone(), newStaff.getAddress(), newStaff.isAdmin(), 0, newStaff.getWarehouseID());
                            System.out.println("Super Admin selected.");
                            break;
                        case "1":
                            newStaff = new Admin(newStaff.getStaffID(), newStaff.getUsername(), newStaff.getPassword(), newStaff.getStaffIC(), newStaff.getName(), newStaff.getAge(), newStaff.getHireDate(), newStaff.getBirthDate(), newStaff.getSalary(), newStaff.getEmail(), newStaff.getPhone(), newStaff.getAddress(), newStaff.isAdmin(), 1, newStaff.getWarehouseID()); // Set HR Admin
                            System.out.println("HR Admin selected.");
                            break;
                        case "2":
                            newStaff = new Admin(newStaff.getStaffID(), newStaff.getUsername(), newStaff.getPassword(), newStaff.getStaffIC(), newStaff.getName(), newStaff.getAge(), newStaff.getHireDate(), newStaff.getBirthDate(), newStaff.getSalary(), newStaff.getEmail(), newStaff.getPhone(), newStaff.getAddress(), newStaff.isAdmin(), 2, newStaff.getWarehouseID()); // Set Warehouse Manager
                            System.out.println("Warehouse Manager selected.");
                            break;
                        case "3":
                            newStaff = new Admin(newStaff.getStaffID(), newStaff.getUsername(), newStaff.getPassword(), newStaff.getStaffIC(), newStaff.getName(), newStaff.getAge(), newStaff.getHireDate(), newStaff.getBirthDate(), newStaff.getSalary(), newStaff.getEmail(), newStaff.getPhone(), newStaff.getAddress(), newStaff.isAdmin(), 3, newStaff.getWarehouseID());; // Set Product Manager
                            System.out.println("Product Manager selected.");
                            break;
                        case "4":
                            newStaff = new Admin(newStaff.getStaffID(), newStaff.getUsername(), newStaff.getPassword(), newStaff.getStaffIC(), newStaff.getName(), newStaff.getAge(), newStaff.getHireDate(), newStaff.getBirthDate(), newStaff.getSalary(), newStaff.getEmail(), newStaff.getPhone(), newStaff.getAddress(), newStaff.isAdmin(), 4, newStaff.getWarehouseID());; // Set Normal Staff (No admin privileges)
                            System.out.println("Normal Staff selected.");
                            break;
                        default:
                            System.out.println("Invalid Input. Please select a valid option from 0 to 4.");
                            break;
                    }
                } while (!adminType.equals("0") && !adminType.equals("1") && !adminType.equals("2") && !adminType.equals("3") && !adminType.equals("4"));

            } else if (adminPrivilege.equals("N")) {
                newStaff.setAdmin(false);
            } else {
                System.out.println("Invalid Input. Please enter 'Y' or 'N'");
            }
        } while (!adminPrivilege.equals("Y") && !adminPrivilege.equals("N"));

        // input the warehouse of the staff
        newStaff.setWarehouseID(wd.getWarehouseToTransfer().getWarehouseId());

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        if (StaffTools.registerStaff(newStaff)) {
            System.out.println("Staff Registered Successfully!");
        } else {
            System.out.println("Staff Registered Failed. Please try again!");
        }
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void updateStaffInformation() {
        Scanner scanner = new Scanner(System.in);
        String fieldToUpdate;
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        boolean check = false;
        String staffID;
        do {
            System.out.print("Enter Staff ID: ");
            staffID = scanner.nextLine();
            if (StaffTools.checkID(staffID)) {
                check = true;
            }else {
                System.out.println("Staff ID does not exist. Please try again.");
            }
        } while(!check);
        Staff staff = StaffTools.retrieveStaff(staffID);
        check = false;

        // Show update options
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Select the field you want to update:");
        System.out.println("[1] Username");
        System.out.println("[2] Password");
        System.out.println("[3] IC");
        System.out.println("[4] Name");
        System.out.println("[5] Age");
        System.out.println("[6] Hire Date");
        System.out.println("[7] Birth Date");
        System.out.println("[8] Salary");
        System.out.println("[9] Email");
        System.out.println("[10] Phone");
        System.out.println("[11] Address");
        System.out.println("[12] Admin Privilege");
        System.out.println("[13] Warehouse ID");

        // Get user's choice
        int choice = Utils.getIntInput("\n> ");

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        // Determine which field the user wants to update
        switch (choice) {
            case 1:
                do {
                    System.out.print("Enter new Username: ");
                    String username = scanner.nextLine();
                    if (!Validator.validateUsername(staff.getUsername())) {
                        System.out.println("Username Exists!");
                    }else {
                        staff.setUsername(username);
                        check = true;
                    }
                }while(!check);
                break;
            case 2:
                System.out.print("Enter new Password: ");
                String password = scanner.nextLine();
                do {
                    if (!Validator.validatePassword(staff.getPassword())) {
                        System.out.println("Invalid Password");
                        System.out.println("Password must contain at least one uppercase letter, one lowercase letter, one number, " +
                                "one symbol, and be between 8 and 12 characters long.");
                    }else {
                        staff.setPassword(password);
                        check = true;
                    }
                }while (!check);
                break;
            case 3:
                do {
                    System.out.print("Enter Staff Malaysia IC: ");
                    staff.setStaffIC(scanner.nextLine());

                    if(!Validator.validateIc(staff.getStaffIC())) {
                        System.out.println("Invalid Ic");
                        System.out.println("IC number should be between 12 digits");
                    }
                } while (!Validator.validateIc(staff.getStaffIC()));
            case 4:
                System.out.print("Enter new Name: ");
                staff.setName(scanner.nextLine());
                break;
            case 5:
                do {
                    int age = Utils.getIntInput("Enter new Age: ");
                    if (age < 0 || age > 200) {
                        System.out.println("Invalid Age!");
                    } else {
                        check = true;
                        staff.setAge(age);
                    }
                }while (!check);
                break;
            case 6:
                do {
                    System.out.print("Enter new Hire Date (YYYY-MM-DD): ");
                    String hireDateString = scanner.nextLine();
                    LocalDate hireDate;
                    // Try parsing the date input to LocalDate
                    try {
                        hireDate = LocalDate.parse(hireDateString);  // Parse the string into a LocalDate object
                        if (!Validator.validateHireDate(hireDate.toString())) {
                            System.out.println("Invalid Hire Date");
                            System.out.println("Hire Date must be in the format YYYY-MM-DD");
                        }else {
                            staff.setBirthDate(hireDate);
                            check = true;
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Please enter the date in the format YYYY-MM-DD.");
                    }
                }while(!check);
                break;
            case 7:
                do {
                    System.out.print("Enter new Birth Date (YYYY-MM-DD): ");
                    String birthDateString = scanner.nextLine();
                    LocalDate birthDate;
                    // Try parsing the date input to LocalDate
                    try {
                        birthDate = LocalDate.parse(birthDateString);  // Parse the string into a LocalDate object
                        if (!Validator.validateDOB(birthDate.toString())) {
                            System.out.println("Invalid Date of Birth");
                            System.out.println("Hire Date must be in the format YYYY-MM-DD");
                        }else {
                            staff.setBirthDate(birthDate);
                            check = true;
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Please enter the date in the format YYYY-MM-DD.");
                    }
                }while(!check);
                break;
            case 8:
                staff.setSalary(Utils.getDoubleInput("Enter new salary: "));
                break;
            case 9:
                do {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    System.out.print("Enter Email: ");
                    staff.setEmail(scanner.nextLine());
                    if (!Validator.validateEmail(staff.getEmail())) {
                        System.out.println("Invalid Email");
                        System.out.println("Email Must End With @xxxxx.com");
                    }
                } while (!Validator.validateEmail(staff.getEmail()));
                staff.setEmail(scanner.nextLine());
                break;
            case 10:
                fieldToUpdate = "StaffPhone";
                System.out.print("Enter new Phone: ");
                staff.setPhone(scanner.nextLine());
                break;
            case 11:
                fieldToUpdate = "StaffAddress";
                System.out.print("Enter new Address: ");
                staff.setAddress(scanner.nextLine());
                break;
            case 12:
                String adminPrivilege;
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                do {

                    System.out.println("Is this staff an Admin? (Y/N): ");
                    adminPrivilege = scanner.nextLine().toUpperCase();
                    if (adminPrivilege.equals("Y")) {
                        staff.setAdmin(true);
                        String adminType;
                        do {

                            System.out.println("[0] Super Admin (Full access to all functions)");
                            System.out.println("[1] HR Admin (Manage Account, User Log)");
                            System.out.println("[2] Warehouse Manager (Manage Warehouse, Stock Management, Check Request Log)");
                            System.out.println("[3] Product Manager (Manage Product)");
                            System.out.println("[4] Normal Staff (No admin privileges)");
                            System.out.print(" > ");

                            adminType = scanner.nextLine();

                            switch (adminType) {
                                case "0":
                                    staff = new Admin(staff.getStaffID(), staff.getUsername(), staff.getPassword(), staff.getStaffIC(), staff.getName(), staff.getAge(), staff.getHireDate(), staff.getBirthDate(), staff.getSalary(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.isAdmin(), 0, staff.getWarehouseID());
                                    System.out.println("Super Admin selected.");
                                    break;
                                case "1":
                                    staff = new Admin(staff.getStaffID(), staff.getUsername(), staff.getPassword(), staff.getStaffIC(), staff.getName(), staff.getAge(), staff.getHireDate(), staff.getBirthDate(), staff.getSalary(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.isAdmin(), 1, staff.getWarehouseID()); // Set HR Admin
                                    System.out.println("HR Admin selected.");
                                    break;
                                case "2":
                                    staff = new Admin(staff.getStaffID(), staff.getUsername(), staff.getPassword(), staff.getStaffIC(), staff.getName(), staff.getAge(), staff.getHireDate(), staff.getBirthDate(), staff.getSalary(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.isAdmin(), 2, staff.getWarehouseID()); // Set Warehouse Manager
                                    System.out.println("Warehouse Manager selected.");
                                    break;
                                case "3":
                                    staff = new Admin(staff.getStaffID(), staff.getUsername(), staff.getPassword(), staff.getStaffIC(), staff.getName(), staff.getAge(), staff.getHireDate(), staff.getBirthDate(), staff.getSalary(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.isAdmin(), 3, staff.getWarehouseID());; // Set Product Manager
                                    System.out.println("Product Manager selected.");
                                    break;
                                case "4":
                                    staff = new Admin(staff.getStaffID(), staff.getUsername(), staff.getPassword(), staff.getStaffIC(), staff.getName(), staff.getAge(), staff.getHireDate(), staff.getBirthDate(), staff.getSalary(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.isAdmin(), 4, staff.getWarehouseID());; // Set Normal Staff (No admin privileges)
                                    System.out.println("Normal Staff selected.");
                                    break;
                                default:
                                    System.out.println("Invalid Input. Please select a valid option from 0 to 4.");
                                    break;
                            }
                        } while (!adminType.equals("0") && !adminType.equals("1") && !adminType.equals("2") && !adminType.equals("3") && !adminType.equals("4"));

                    } else if (adminPrivilege.equals("N")) {
                        staff.setAdmin(false);
                    } else {
                        System.out.println("Invalid Input. Please enter 'Y' or 'N'");
                    }
                } while (!adminPrivilege.equals("Y") && !adminPrivilege.equals("N"));
                break;
            case 13:
                fieldToUpdate = "WarehouseID";

                do {
                    System.out.print("Enter new Warehouse ID: ");
                    String warehouseID = scanner.nextLine();
                    if(WarehouseTools.isWarehouseIDExists(warehouseID)) {
                        check = true;
                        staff.setWarehouseID(warehouseID);
                    }else {
                        System.out.println("Invalid Warehouse ID!");
                    }
                }while (!check);
                break;
            default:
                System.out.println("Invalid choice.");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
        }

        // Update the chosen field in the database
        StaffTools.updateStaff(staff);
    }

    public static void deleteStaff() {
        Scanner scanner = new Scanner(System.in);
        List<Staff> staffList = StaffTools.retrieveAllStaff();  // Retrieve all staff from the database

        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
            return;
        }

        int totalIndex = staffList.size();
        final int staffPerPage = 5;  // Set the number of staff to display per page
        int page = 0;
        int maxPages = (totalIndex - 1) / staffPerPage;
        boolean exit = false;

        do {
            int count = 1;
            int startIndex = page * staffPerPage;
            int endIndex = Math.min(startIndex + staffPerPage, totalIndex);

            // Display staff on the current page
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("============================================================================================================================");
            System.out.printf("|%-3s|%-15s|%-20s|%-20s|%-20s|\n", "No.", "Staff ID", "Staff Name", "Position", "Warehouse ID");

            for (int i = startIndex; i < endIndex; i++) {
                Staff staff = staffList.get(i);
                String position = staff.isAdmin() ? getAdminPosition(((Admin) staff).getPrivilege()) : "Staff";  // Check if admin
                System.out.println("============================================================================================================================");
                System.out.printf("|%-3d|%-15s|%-20s|%-20s|%-20s|\n", count, staff.getStaffID(), staff.getName(), position, staff.getWarehouseID());
                count++;
            }

            System.out.println("============================================================================================================================");
            System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
            System.out.printf("Total staff: %d\n", totalIndex);
            System.out.println("[\"A\" for previous page]\t\t[\"Q\" to exit]\t\t[\"D\" for next page]");
            System.out.print("Select staff by number or enter navigation option: ");

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
                        if (Character.isDigit(option)) {
                            int num = Character.getNumericValue(option);
                            if (num >= 1 && num <= (endIndex - startIndex)) {
                                Staff selectedStaff = staffList.get(startIndex + num - 1);

                                // Confirmation before deletion
                                if (confirmDeletion(selectedStaff, scanner)) {
                                    StaffTools.deleteStaff(selectedStaff.getStaffID());
                                }
                                return;
                            }
                        }
                        System.out.println("Invalid input. Please enter a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a single character.");
            }
        } while (!exit);
    }

    public static void showAllStaff() {
        Scanner scanner = new Scanner(System.in);
        List<Staff> staffList = StaffTools.retrieveAllStaff();  // Retrieve all staff from the database

        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
            return;
        }

        int totalIndex = staffList.size();
        final int staffPerPage = 5;  // Set the number of staff to display per page
        int page = 0;
        int maxPages = (totalIndex - 1) / staffPerPage;
        boolean exit = false;

        do {
            int count = 1;
            int startIndex = page * staffPerPage;
            int endIndex = Math.min(startIndex + staffPerPage, totalIndex);

            // Display staff on the current page
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("========================================================================================================================");
            System.out.printf("|%-3s|%-15s|%-20s|%-20s|%-20s|\n", "No.", "Staff ID", "Staff Name", "Position", "Warehouse ID");

            for (int i = startIndex; i < endIndex; i++) {
                Staff staff = staffList.get(i);
                String position = staff.isAdmin() ? getAdminPosition(((Admin) staff).getPrivilege()) : "Staff";  // Check if admin
                System.out.println("========================================================================================================================");
                System.out.printf("|%-3d|%-15s|%-20s|%-20s|%-20s|\n", count, staff.getStaffID(), staff.getName(), position, staff.getWarehouseID());
                count++;
            }

            System.out.println("========================================================================================================================");
            System.out.printf("Page %d of %d\n", page + 1, maxPages + 1);
            System.out.printf("Total staff: %d\n", totalIndex);
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
                        System.out.println("Invalid input. Please enter a valid navigation option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a single character.");
            }
        } while (!exit);
    }

    // Function to confirm the deletion of a selected staff
    private static boolean confirmDeletion(Staff staff, Scanner scanner) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Staff Detail Confirmation");
        System.out.println("============================================================================================================================");
        System.out.printf("|%-15s|%-20s|%-20s|%-20s|\n", "Staff ID", "Staff Name", "Position", "Warehouse ID");
        System.out.println("============================================================================================================================");
        System.out.printf("|%-15s|%-20s|%-20s|%-20s|\n", staff.getStaffID(), staff.getName(), staff.isAdmin() ? getAdminPosition(((Admin) staff).getPrivilege()) : "Staff", staff.getWarehouseID());
        System.out.println("============================================================================================================================");

        String confirmation;
        do {
            System.out.print("Confirm deletion of this staff? <Y/N> : ");
            confirmation = scanner.nextLine().trim().toUpperCase();

            if (confirmation.equals("Y")) {
                return true;  // Confirm deletion
            } else if (confirmation.equals("N")) {
                return false;  // Cancel deletion
            } else {
                System.out.println("Invalid input! Please enter Y or N.");
            }
        } while (!confirmation.equals("Y") && !confirmation.equals("N"));

        return false;
    }

    // Function to retrieve admin position based on the privilege
    private static String getAdminPosition(int adminPrivilege) {
        switch (adminPrivilege) {
            case 0:
                return "Super Manager";
            case 1:
                return "HR Admin";
            case 2:
                return "Warehouse Manager";
            case 3:
                return "Product Manager";
            default:
                return "Unknown Admin Position";
        }
    }

    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.print("Enter username: ");
        staff.setUsername(scanner.nextLine());
        if (!StaffTools.checkUsername(staff.getUsername())) {
            System.out.println("Invalid username!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        System.out.print("Enter password: ");
        staff.setPassword(scanner.nextLine());
        if (!StaffTools.checkPassword(staff.getUsername(), staff.getPassword())) {
            System.out.println("Incorrect password!");
            try {
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        int adminPrivilege = StaffTools.getStaffPrivilege(username);
        TransferDAO tDAO = new TransferDAO();
        int choice;
        do {

            // Display menu based on admin privilege
            System.out.println("\n\n\n\n\n\n\n\n\n\nAdmin Menu");
            System.out.println("==============================");
            // Full access for Super Admin (privilege = 0)
            if (adminPrivilege == 0) {
                System.out.println("[1] Manage Retailer");
                System.out.println("[2] Manage Warehouse");
                System.out.println("[3] Manage Account");
                System.out.println("[4] Manage Product");
                System.out.println("[5] Stock In");
                System.out.println("[6] Stock Transfer");
                System.out.println("[7] Stock Distribution");
                System.out.println("[8] Stock Request");
                System.out.println("[9] Check User Log");
                System.out.println("[10] Staff Request Log");
            }

            // HR Admin (privilege = 1)
            else if (adminPrivilege == 1) {
                System.out.println("[ ] Manage Retailer");
                System.out.println("[ ] Manage Warehouse");
                System.out.println("[3] Manage Account");
                System.out.println("[ ] Manage Product");
                System.out.println("[ ] Stock In");
                System.out.println("[ ] Stock Transfer");
                System.out.println("[ ] Stock Distribution");
                System.out.println("[ ] Stock Request");
                System.out.println("[9] Check User Log");
                System.out.println("[  ] Staff Request Log");
            }

            // Warehouse Manager (privilege = 2)
            else if (adminPrivilege == 2) {
                System.out.println("[ ] Manage Retailer");
                System.out.println("[2] Manage Warehouse");
                System.out.println("[ ] Manage Account");
                System.out.println("[ ] Manage Product");
                System.out.println("[5] Stock In");
                System.out.println("[6] Stock Transfer");
                System.out.println("[7] Stock Distribution");
                System.out.println("[8] Stock Request");
                System.out.println("[ ] Check User Log");
                System.out.println("[10] Staff Request Log");
            }

            // Product Manager (privilege = 3)
            else if (adminPrivilege == 3) {
                System.out.println("[ ] Manage Retailer");
                System.out.println("[ ] Manage Warehouse");
                System.out.println("[ ] Manage Account");
                System.out.println("[4] Manage Product");
                System.out.println("[ ] Stock In");
                System.out.println("[ ] Stock Transfer");
                System.out.println("[ ] Stock Distribution");
                System.out.println("[ ] Stock Request");
                System.out.println("[ ] Check User Log");
                System.out.println("[  ] Staff Request Log");
            }

            // Normal Staff (No Admin Privilege)
            else {
                System.out.println("[ ] Manage Retailer");
                System.out.println("[ ] Manage Warehouse");
                System.out.println("[ ] Manage Account");
                System.out.println("[ ] Manage Product");
                System.out.println("[5] Stock In");
                System.out.println("[6] Stock Transfer");
                System.out.println("[7] Stock Distribution");
                System.out.println("[8] Stock Request");
                System.out.println("[9] Check User Log");
                System.out.println("[  ] Staff Request Log");
            }

            // Common exit option
            System.out.println("[11] Exit");
            System.out.println("==============================");
            choice = Utils.getIntInput("Select your option: ");

            // Process choices based on privilege level
            switch (choice) {
                case 1:
                    if (adminPrivilege == 0) {
                        // Super Admin: Manage Retailer
                        RetailerDAO.retailerMenu();
                    }
                    break;
                case 2:
                    if (adminPrivilege == 0 || adminPrivilege == 2) {
                        // Super Admin and Warehouse Manager: Manage Warehouse
                        WarehouseDAO.warehouseMenu();
                    }
                    break;
                case 3:
                    if (adminPrivilege == 0 || adminPrivilege == 1) {
                        // Super Admin and HR Admin: Manage Account
                        ManageStaffMenu();
                    }
                    break;
                case 4:
                    if (adminPrivilege == 0 || adminPrivilege == 3) {
                        // Super Admin and Product Manager: Manage Product
                        ProductDAO.productMenu();
                    }
                    break;
                case 5:
                    // All Admins and Staff: Stock In

                    break;
                case 6:
                    // All Admins and Staff: Stock Transfer
                    tDAO.transferStock(StaffTools.getStaffWarehouseID(username));
                    break;
                case 7:
                    // All Admins and Staff: Stock Distribution
                    tDAO.distributeStock(StaffTools.getStaffWarehouseID(username));
                    break;
                case 8:
                    // All Admins and Staff: Stock Request
                    StockRequestDAO.requestStock();
                    break;
                case 9:
                    if (adminPrivilege == 0 || adminPrivilege == 1) {
                        // Super Admin and HR Admin: Check User Log

                    }
                    break;
                case 10:
                    if (adminPrivilege == 0 || adminPrivilege == 2) {
                        // Super Admin and Warehouse Manager: Staff Request Log

                    }
                    break;
                case 11:
                    return;  // Exit
                default:
                    System.out.println("Invalid choice, please try again.");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } while (choice != 11);
    }

    public static void staffMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        TransferDAO tDAO = new TransferDAO();
        int choice;
        do {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Staff Menu");
            System.out.println("=======================");
            System.out.println("[1] Stock In");
            System.out.println("[2] Stock Transfer");
            System.out.println("[3] Stock Distribution");
            System.out.println("[4] Stock Request");
            System.out.println("[5] Exit");
            System.out.println("=======================");
            choice = Utils.getIntInput("Select your option:");


            switch (choice) {
                case 1:
                    break;
                case 2:
                    tDAO.transferStock(StaffTools.getStaffWarehouseID(username));
                    break;
                case 3:
                    tDAO.distributeStock(StaffTools.getStaffWarehouseID(username));
                    break;
                case 4:
                    StockRequestDAO.requestStock();
                    break;
                case 5:
                    return;
                default:
                    System.out.print("Please try again for select");
                    break;
            }
        } while (choice != 0);
    }

    public static void ManageStaffMenu() {
        int option = 999;

        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Manage Product");
            System.out.println("[1] Display All Staff");
            System.out.println("[2] Add new Staff");
            System.out.println("[3] Delete Staff");
            System.out.println("[4] Update Staff");
            System.out.println("[5] Exit");

            option = Utils.getIntInput("Please select an option: ");
            switch(option){
                case 1:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    showAllStaff();
                    break;
                case 2:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    registerStaff();
                    break;
                case 3:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    deleteStaff();
                    break;
                case 4:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    updateStaffInformation();
                    break;
                case 5:
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                    break;
                default:
                    System.out.println("Invalid option!");
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }while(option != 5);
    }
}
