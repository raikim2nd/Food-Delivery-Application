package com.raiki.app.rest.controller;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Method getWeather returns all the weather data that is currently stored in the database.
     * @return List<Weather>, which holds all the weather data as Weather objects in a list.

     * Currently, has no "function" in the project, but good for checking the database entries for now. Might
        be needed in the future for another view in the frontend for example.
     */
    @GetMapping("/weather")
    public List<Weather> getWeather() {
        return weatherService.getAllWeatherData();
    }
}
