package Entity;

import java.time.LocalDateTime;

public class Log {
    private LocalDateTime time;
    private String staffID;

    public Log(){

    }

    public Log(LocalDateTime time, String staffID) {
        this.time = time;
        this.staffID = staffID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
}
