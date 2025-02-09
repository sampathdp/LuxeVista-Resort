package com.dreampixel.luxevistaresort;

public class BookingHistoryItem {
    private String type;
    private String currentDate;
    private String name;
    private String checkinDate;
    private String checkoutDate;
    private Double price;

    public BookingHistoryItem(String type, String currentDate, String name, String checkinDate, String checkoutDate, Double price) {
        this.type = type;
        this.currentDate = currentDate;
        this.name = name;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getName() {
        return name;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public Double getPrice() {
        return price;
    }
}
