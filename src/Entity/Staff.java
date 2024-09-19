package Entity;

import java.time.LocalDate;

public class Staff {
    protected String staffID;
    protected String username;
    protected String password;
    protected String staffIC;
    protected String name;
    protected int age;
    protected LocalDate hireDate;
    protected LocalDate birthDate;
    protected double salary;
    protected String email;
    protected String phone;
    protected String address;
    protected boolean isAdmin;
    protected String warehouseID;

    public Staff() {

    }

    public Staff(String staffID, String username, String password, String staffIC, String name, int age, LocalDate hireDate, LocalDate birthDate, double salary, String email, String phone, String address, boolean isAdmin, String warehouseID) {
        this.staffID = staffID;
        this.username = username;
        this.password = password;
        this.staffIC = staffIC;
        this.name = name;
        this.age = age;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.isAdmin = isAdmin;
        this.warehouseID = warehouseID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffIC() {
        return staffIC;
    }

    public void setStaffIC(String staffIC) {
        this.staffIC = staffIC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }
}
