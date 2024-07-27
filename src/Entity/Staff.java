package Entity;

public class Staff {
    private String StaffID;
    private String StaffName;
    private String StaffPosition;

    public Staff(String staffID, String StaffName, String StaffPosition) {
        this.StaffID = staffID;
        this.StaffName = StaffName;
        this.StaffPosition = StaffPosition;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        this.StaffID = staffID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        this.StaffName = staffName;
    }

    public String getStaffPosition() {
        return StaffPosition;
    }

    public void setStaffPosition(String staffPosition) {
        this.StaffPosition = staffPosition;
    }
}
