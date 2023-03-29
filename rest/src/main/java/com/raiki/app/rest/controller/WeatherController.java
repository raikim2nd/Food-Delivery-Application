package com.raiki.app.rest.controller;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.delivery.DeliveryData;
import com.raiki.app.rest.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping("/weather")
    public List<Weather> getWeather() {
        return weatherService.getAllWeatherData();
    }


    // enne muudatusi returnis stringi, proovida selle exceptioniga
    /*
    @PostMapping("/delivery")
    public float calculateDeliveryFee(@RequestBody DeliveryData deliveryData) throws Exception {
        System.out.println("Deliverydata: " + deliveryData.getCity() + " - " + deliveryData.getVehicle());
        return switch (deliveryData.getVehicle()) {
            case "Car" -> weatherService.calculateDeliveryFeeForCar(deliveryData.getCity());
            case "Scooter" -> weatherService.calculateDeliveryFeeForScooter(deliveryData.getCity());
            case "Bike" -> weatherService.calculateDeliveryFeeForBike(deliveryData.getCity());
            default -> 0;
        };
    }
     */

    /*
    @GetMapping("/weather")
    public List<Weather> getWeather() {
        return (List<Weather>) weatherRepository.findAll();
    }
     */

    /*
    @PostMapping("/save")
    public String addWeather(@RequestBody Weather weather) {
        weatherRepository.save(weather);
        return "Saved...";
        //weatherRepository.save()
    }
     */

    /*
    @DeleteMapping("/delete/{id}")
    public String deleteWeather(@PathVariable int id) {
        Weather deleteWeather = weatherRepository.findById(id).get();
        weatherRepository.delete(deleteWeather);
        return "Delete user with the id: " + id;
    }
     */

}
