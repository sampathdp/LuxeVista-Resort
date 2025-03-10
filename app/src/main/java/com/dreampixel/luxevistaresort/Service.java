package com.dreampixel.luxevistaresort;

public class Service {
    private int id;
    private String name;
    private String description;
    private double price;
    private int availability;
    private byte[] image;

    public Service(int id, String name, String description, double price, int availability, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
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

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    public byte[] getImage() {
        return image;
    }

}
