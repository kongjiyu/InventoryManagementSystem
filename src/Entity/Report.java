package Entity;

import java.time.LocalDateTime;

public class Report {
    private LocalDateTime date;
    private double totalSales;
    private String topSalesProduct;
    private int tolSalesProductQuantity;
    private String topSalesStaff;
    private double topSalesStaffPerformance;

    public Report(){

    }

    public Report(LocalDateTime date, double totalSales, String topSalesProduct, int tolSalesProductQuantity, String topSalesStaff, double topSalesStaffPerformance) {
        this.date = date;
        this.totalSales = totalSales;
        this.topSalesProduct = topSalesProduct;
        this.tolSalesProductQuantity = tolSalesProductQuantity;
        this.topSalesStaff = topSalesStaff;
        this.topSalesStaffPerformance = topSalesStaffPerformance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public String getTopSalesProduct() {
        return topSalesProduct;
    }

    public void setTopSalesProduct(String topSalesProduct) {
        this.topSalesProduct = topSalesProduct;
    }

    public int getTolSalesProductQuantity() {
        return tolSalesProductQuantity;
    }

    public void setTolSalesProductQuantity(int tolSalesProductQuantity) {
        this.tolSalesProductQuantity = tolSalesProductQuantity;
    }

    public String getTopSalesStaff() {
        return topSalesStaff;
    }

    public void setTopSalesStaff(String topSalesStaff) {
        this.topSalesStaff = topSalesStaff;
    }

    public double getTopSalesStaffPerformance() {
        return topSalesStaffPerformance;
    }

    public void setTopSalesStaffPerformance(double topSalesStaffPerformance) {
        this.topSalesStaffPerformance = topSalesStaffPerformance;
    }
}
