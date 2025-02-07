package com.dreampixel.luxevistaresort;

public class Room {
    private int id;
    private String type;
    private String description;
    private double price;
    private byte[] image;

    public Room(int id, String type, String description, double price, byte[] image) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }
}