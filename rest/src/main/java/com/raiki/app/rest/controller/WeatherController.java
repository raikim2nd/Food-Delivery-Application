package com.raiki.app.rest.controller;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.weather.DeliveryData;
import com.raiki.app.rest.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
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

    @PostMapping("/delivery")
    public String calculateDeliveryFee(@RequestBody DeliveryData deliveryData) {
        System.out.println(deliveryData.getCity() + " : " + deliveryData.getVehicle());
        return weatherService.calculateDeliveryFee(deliveryData.getCity(), deliveryData.getVehicle());
    }

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
