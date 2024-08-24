package Entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Admin extends Staff {
    ArrayList<String> privilege = new ArrayList<>();

    public Admin() {
        super();
    }

    public Admin(String staffID, String password, String staffIC, String name, int age, LocalDate hireDate, LocalDate birthDate, double salary, String email, String phone, String address, ArrayList<String> privilege) {
        super(staffID, password, staffIC, name, age, hireDate, birthDate, salary, email, phone, address);
        this.privilege = privilege;
    }

    public ArrayList<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(ArrayList<String> privilege) {
        this.privilege = privilege;
    }
}
