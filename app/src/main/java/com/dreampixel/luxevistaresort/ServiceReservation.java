package com.dreampixel.luxevistaresort;

public class ServiceReservation {
    private String reservationDateTime;
    private String serviceName;
    public ServiceReservation(String reservationDateTime, String serviceName) {

        this.reservationDateTime = reservationDateTime;
        this.serviceName = serviceName;
    }

    public String getReservationDateTime() {
        return reservationDateTime;
    }

    public String getServiceName() {
        return serviceName;
    }
}
