package com.raiki.app.rest.controller;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.repository.WeatherRepository;
import com.raiki.app.rest.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// siia vist lisada ka @RequestMapping
@RestController
//@RequestMapping("/") hetkel töötab ka ilma selleta
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    // siia ilmselt repot pole vaja selliselt, vaid hoopis @Autowired WeatherService
    //@Autowired
    //private WeatherRepository weatherRepository;


    // meetodi parameetrisse vist @RequestBody annotatsioon, kui service klassi meetodil on mingid sisendid


    @GetMapping("/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping("/weather")
    public List<Weather> getWeather() {
        return weatherService.getAllWeatherData();
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
