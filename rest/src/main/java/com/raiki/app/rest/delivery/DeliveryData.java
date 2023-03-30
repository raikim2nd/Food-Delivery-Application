package com.raiki.app.rest.delivery;

/**
 * Class for data exchange between front- and backend.
 */
public class DeliveryData {

    private final String city;
    private final String vehicle;

    public DeliveryData(String city, String vehicle) {
        this.city = city;
        this.vehicle = vehicle;
    }

    public String getCity() {
        return city;
    }

    public String getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "DeliveryData{" +
                "city='" + city + '\'' +
                ", vehicle='" + vehicle + '\'' +
                '}';
    }
}
