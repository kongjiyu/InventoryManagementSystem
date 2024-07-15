package Entity;

public class Staff {
    private String staffID;
    private String StaffName;
    private String StaffPosition;

    public Staff(String staffID, String StaffName, String StaffPosition) {
        this.staffID = staffID;
        this.StaffName = StaffName;
        this.StaffPosition = StaffPosition;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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
