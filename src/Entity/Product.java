package Entity;

import Model.Dimension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Product {
    private int UPC;
    private String name;
    private String desc;
    private String category;
    private double price;
    private double weight;
    private Dimension dimension;
    private LocalDateTime updatedAt;

    public Product(){
        dimension = new Dimension();
    }

    public Product(int UPC, String name, String desc, String category, double price, double weight, Dimension dimension, LocalDateTime updatedAt) {
        this.UPC = UPC;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.dimension = dimension;
        this.updatedAt = updatedAt;
    }

    public int getUPC() {
        return UPC;
    }

    public void setUPC(int UPC) {
        this.UPC = UPC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
