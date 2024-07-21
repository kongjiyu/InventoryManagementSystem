package Entity;

import java.util.ArrayList;

public class Admin extends Staff {
    ArrayList<String> privilege = new ArrayList<>();

    public Admin(String staffID, String StaffName, String StaffPosition) {
        super(staffID, StaffName, StaffPosition);
    }
}
