package Entity;

import java.time.LocalDate;

public class Product {
    private String SKU;
    private String name;
    private String desc;
    private String category;
    private double price;
    private double weight;
    private double dimension;
    private int quantity;
    private LocalDate expDate;
    private LocalDate updatedAt;

    public Product(String SKU, String name, String desc, String category, double price, double weight, double dimension, int quantity, LocalDate expDate, LocalDate updatedAt) {
        this.SKU = SKU;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.dimension = dimension;
        this.quantity = quantity;
        this.expDate = expDate;
        this.updatedAt = updatedAt;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
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

    public double getDimension() {
        return dimension;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
