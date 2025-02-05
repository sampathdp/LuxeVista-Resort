package com.dreampixel.luxevistaresort;

public class Room {
    private int roomId;
    private String type;
    private String description;
    private int maxOccupancy;
    private double pricePerNight;
    private int roomCount;
    private byte[] roomImage; // Using Blob data type

    public Room(String type, String description, int maxOccupancy, double pricePerNight, int roomCount, byte[] roomImage) {
        this.type = type;
        this.description = description;
        this.maxOccupancy = maxOccupancy;
        this.pricePerNight = pricePerNight;
        this.roomCount = roomCount;
        this.roomImage = roomImage;
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getMaxOccupancy() { return maxOccupancy; }
    public void setMaxOccupancy(int maxOccupancy) { this.maxOccupancy = maxOccupancy; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public int getRoomCount() { return roomCount; }
    public void setRoomCount(int roomCount) { this.roomCount = roomCount; }

    public byte[] getRoomImage() { return roomImage; }
    public void setRoomImage(byte[] roomImage) { this.roomImage = roomImage; }
}
