package com.raiki.app.rest.weather;

import com.raiki.app.rest.repository.WeatherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    private AutoCloseable autoCloseable;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService();
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    /*
    @Test
    void canGetAllWeather() {
        weatherService.getAllWeatherData();
        verify(weatherRepository).findAll();
    }

     */

    // will initiate insertWeatherToDatabase() method, so lets check if stations Tartu-Tõravere, Tallinn-Harku and Pärnu were inserted to database
   /*
    @Test
    void getNeededStations() {
        // find how many entities are in the database before insertion
        int beforeInsertion = weatherService.getAllWeatherData().size();
        System.out.println(beforeInsertion);
        // add the info of our three needed stations to database and check the size
        weatherService.getNeededStations();
        int afterInsertion = weatherService.getAllWeatherData().size();
        assertEquals(3, afterInsertion-beforeInsertion);

    }

    */

    /*
    @Test
    void insertWeatherToDatabase() {
        // find how many entities are in the database before insertion
        int beforeInsertion = weatherService.getAllWeatherData().size();
        System.out.println(beforeInsertion);
        // add the info of our three needed stations to database and check the size
        List<Station> stationList = new ArrayList<>();
        Station station = new Station();
        station.setName("Tallinn");
        station.setWmocode(123L);
        station.setAirtemperature(1.0f);
        station.setWindspeed(2.0f);
        station.setPhenomenon("Rain");
        station.setTimestamp(321L);
        stationList.add(station);
        System.out.println(stationList.size() + " - size");
        weatherService.insertWeatherToDatabase(stationList);
        int afterInsertion = weatherService.getAllWeatherData().size();
        weatherRepository.save(new Weather(null,station.getName(),station.getWmocode(),station.getAirtemperature(),station.getWindspeed(),
                station.getPhenomenon(),station.getTimestamp()));
        Iterable<Weather> all = weatherRepository.findAll();
        System.out.println(all.toString());
        System.out.println("After:" + afterInsertion);
        assertEquals(1, afterInsertion-beforeInsertion);
    }

     */

}