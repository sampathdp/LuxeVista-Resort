package com.dreampixel.luxevistaresort;

public class Place {
    String name;
    String description;
    String distance;
    float rating;
    int imageResId;

    public Place(String name, String description, String distance, float rating, int imageResId) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.rating = rating;
        this.imageResId = imageResId;
    }

}
