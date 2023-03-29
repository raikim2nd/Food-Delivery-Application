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
    DeliveryService deliveryService;

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
