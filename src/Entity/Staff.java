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

    public void printBye(){
        System.out.println("Bye bye!");
    }

    public void printHallo(){
        System.out.println("Hallo");
    }

    public void printHello(){
        System.out.println("Hello World!");
    }

    public void renJunFunction(){
        System.out.println("I'm Ren Jun");
    }

    public void alexFunction(){
        System.out.println("I'm Alex");
    }
  
    public void jishouFucntion(){
        System.out.println("I'm Jishou");
    }

    public void jyFunction() { System.out.println("I'm Jy"); }

    public void alexhahaFunction() {
        System.out.println("My name is uwuveuvueevu onyeteyewe uguemugue osas");
    }

}
