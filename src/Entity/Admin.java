package Entity;

import DatabaseTools.StaffTools;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Staff {
    ArrayList<String> privilege = new ArrayList<>();

    public Admin() {
        super();
    }

    public Admin(String staffID, String username, String password, String staffIC, String name, int age, LocalDate hireDate, LocalDate birthDate, double salary, String email, String phone, String address, boolean isAdmin, ArrayList<String> privilege) {
        super(staffID, username, password, staffIC, name, age, hireDate, birthDate, salary, email, phone, address, isAdmin);
        this.privilege = privilege;
    }

    public ArrayList<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(ArrayList<String> privilege) {
        this.privilege = privilege;
    }

    public class staffRegister {
        public static void registerStaff() {
            Scanner sc = new Scanner(System.in);
            Staff newStaff = new Staff();

            System.out.print("Enter Staff ID: ");
            newStaff.setStaffID(sc.nextLine());

            if (StaffTools.checkStaffID(newStaff.getStaffID())) {
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
                if(!Validator.validateIc(newStaff.getStaffIC())) {
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
                if(!Validator.validateDOB(newStaff.getBirthDate().toString())) {
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
            }else {
                System.out.println("Staff Registered Failed. Please try again!");
            }
        }
    }
}
