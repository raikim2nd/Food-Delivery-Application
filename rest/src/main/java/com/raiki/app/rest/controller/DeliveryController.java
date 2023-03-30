package com.raiki.app.rest.controller;

import com.raiki.app.rest.delivery.DeliveryData;
import com.raiki.app.rest.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    /**
     * Method calculateDeliveryFee returns the delivery fee for given parameters, that the user has chosen in the
        REST interface.
     * Calculations are done based on the latest weather data and business rules. CalculateDeliveryFee method uses
        DeliveryService class methods for calculations and receiving the latest weather info.
     * Method is needed for communication and data exchange between front- and backend.

     * @param deliveryData - DeliveryData object from the REST interface, for example: "city": Tallinn and "vehicle": Car
     * @return float as delivery fee based on the weather data and business rules for the calculations.
     * @throws Exception - in case vehicle type is Bike and wind speed is greater than 20 m/s OR
        vehicle type is Bike/Scooter and weather phenomenon is glaze, hail or thunder, then method throws an Exception.
     */

    @PostMapping("/delivery")
    public float calculateDeliveryFee(@RequestBody DeliveryData deliveryData) throws Exception {
        System.out.println("Deliverydata: " + deliveryData.getCity() + " - " + deliveryData.getVehicle());
        return switch (deliveryData.getVehicle()) {
            case "Car" -> deliveryService.calculateDeliveryFeeForCar(deliveryData.getCity());
            case "Scooter" -> deliveryService.calculateDeliveryFeeForScooter(deliveryData.getCity());
            case "Bike" -> deliveryService.calculateDeliveryFeeForBike(deliveryData.getCity());
            default -> 0;
        };
    }

}
