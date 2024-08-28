package Entity;

import Model.Dimension;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private String UPC;
    private String name;
    private String desc;
    private String category;
    private double price;
    private double weight;
    private Dimension dimension;
    private int quantity;
    private LocalDate updatedAt;

    public Product(){
        dimension = new Dimension();
    }

    public Product(String UPC, String name, String desc, String category, double price, double weight, Dimension dimension, int quantity, LocalDate updatedAt) {
        this.UPC = UPC;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.dimension = dimension;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
