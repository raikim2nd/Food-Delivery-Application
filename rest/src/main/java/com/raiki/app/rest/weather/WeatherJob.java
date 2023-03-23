package com.raiki.app.rest.weather;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherJob{

    private WeatherService weatherService;

    private WeatherJob(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // at minute 15 every hour
    @Scheduled(cron = "0 15 * * * *")
    public void addWeatherToDatabase() {
        weatherService.getNeededStations();
    }
}
