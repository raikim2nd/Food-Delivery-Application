package com.raiki.app.rest.repository;

import com.raiki.app.rest.model.Weather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class WeatherRepositoryTest {

    @Autowired
    private WeatherRepository weatherRepository;

    /*
    @Test
    void checkIfWeatherViljandiExists () {
        Weather weather = new Weather(1500,"Viljandi",123L,1.0f,2.0f,"Heavy rain",12345L);
        weatherRepository.save(weather);
        Optional<Weather> viljandi = weatherRepository.findById(1500);
        assertEquals(weather,viljandi.get());

    }

     */

}