package com.dreampixel.luxevistaresort;

public class ServiceReservation {

    private int reservationId;
    private int serviceId;
    private int userId;
    private String currentDate;
    private String reservationDateTime;

    public ServiceReservation(int reservationId, int serviceId, int userId, String currentDate, String reservationDateTime) {
        this.reservationId = reservationId;
        this.serviceId = serviceId;
        this.userId = userId;
        this.currentDate = currentDate;
        this.reservationDateTime = reservationDateTime;
    }

    public String getReservationDateTime() {
        return reservationDateTime;

    }
}
