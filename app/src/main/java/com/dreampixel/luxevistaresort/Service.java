package com.dreampixel.luxevistaresort;

public class Service {
    private int id;
    private String name;
    private String description;
    private double price;
    private int availability;
    private int categoryId;
    private byte[] image;

    public Service(int id, String name, String description, double price, int availability, int categoryId, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.categoryId = categoryId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
