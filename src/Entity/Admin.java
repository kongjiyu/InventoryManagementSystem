package Entity;

import DatabaseTools.StaffTools;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Staff {
    // for checking the admin in which position
    // 0 - super manager, have all function
    // 1 - HR admin (Manage Account, user log)
    // 2 - Warehouse Manager(Manage Warehouse, Stock Management, check request log)
    // 3 - Product Manager (Manage Product)
    // null - normal staff
    int privilege;

    public Admin() {
        super();
    }

    public Admin(String staffID, String username, String password, String staffIC, String name, int age, LocalDate hireDate, LocalDate birthDate, double salary, String email, String phone, String address, boolean isAdmin, int privilege, String warehouseID) {
        super(staffID, username, password, staffIC, name, age, hireDate, birthDate, salary, email, phone, address, isAdmin, warehouseID);
        this.privilege = privilege;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
}
