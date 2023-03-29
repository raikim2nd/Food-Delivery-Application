package com.raiki.app.rest.delivery;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.weather.WeatherRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public DeliveryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public float calculateDeliveryFeeForCar(String city){
        return switch (city) {
            case "Tallinn" -> 4;
            case "Tartu" -> 3.5f;
            case "Pärnu" -> 3f;
            default -> 0;
        };
    }

    public float calculateDeliveryFeeForScooter(String city) throws Exception {
        float deliveryFee = 0f;
        String sql = "";
        // regular base fee
        switch (city) {
            case "Tallinn": {
                deliveryFee += 3.5f;
                sql = "select * from weather where name='Tallinn-Harku' order by timestamp DESC limit 1";
                break;
            }
            case "Tartu": {
                deliveryFee += 3f;
                sql = "select * from weather where name='Tartu-Tõravere' order by timestamp DESC limit 1";
                break;
            }
            case "Pärnu": {
                deliveryFee += 2.5f;
                sql = "select * from weather where name='Pärnu' order by timestamp DESC limit 1";
                break;
            }
        }
        Weather latestWeather = jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
        System.out.println(latestWeather.toString());
        return deliveryFee + getAirTemperatureExtraFee(latestWeather.temperature()) + getWeatherPhenomenonExtraFee("Thunder");
    }

    public float calculateDeliveryFeeForBike(String city) throws Exception {
        System.out.println("City: " + city);
        float deliveryFee = 0f;
        String sql = "";
        // regular base fee
        switch (city) {
            case "Tallinn": {
                deliveryFee += 3f;
                sql = "select * from weather where name='Tallinn-Harku' order by timestamp DESC limit 1";
                break;
            }
            case "Tartu": {
                deliveryFee += 2.5f;
                sql = "select * from weather where name='Tartu-Tõravere' order by timestamp DESC limit 1";
                break;
            }
            case "Pärnu": {
                deliveryFee += 2f;
                sql = "select * from weather where name='Pärnu' order by timestamp DESC limit 1";
                break;
            }
        }
        Weather latestWeather = jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
        return deliveryFee + getAirTemperatureExtraFee(latestWeather.temperature()) +
                getWindSpeedExtraFee(latestWeather.windspeed()) + getWeatherPhenomenonExtraFee(latestWeather.phenomenon());
    }

    public float getAirTemperatureExtraFee(float temperature) {
        if (temperature < -10f) return 1f;
        else if (temperature >= -10f && temperature <= 0f) return 0.5f;
        else return 0;
    }

    public float getWindSpeedExtraFee(float windspeed) throws Exception {
        if (windspeed > 20f) throw new Exception();
        else if (windspeed <= 20f && windspeed >= 10f) return 0.5f;
        else return 0;
    }

    // SIIA LISADA VEEL ILMAOLUSID, MIS PUUDUTAVAD VIHMA!
    public float getWeatherPhenomenonExtraFee(String phenomenon) throws Exception {
        if (phenomenon.contains("snow") || phenomenon.contains("sleet")) return 1f;
        else if (phenomenon.contains("rain")) return 0.5f;
        else if (phenomenon.equals("Glaze") || phenomenon.equals("Hail") ||
                phenomenon.toLowerCase().contains("thunder")) throw new Exception();
        else return 0;
    }
}
