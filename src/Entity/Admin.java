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
}
