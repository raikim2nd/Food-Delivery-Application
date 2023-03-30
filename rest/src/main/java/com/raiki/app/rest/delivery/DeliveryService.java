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

    /**
     * Method calculateDeliveryFeeForCar calculates the delivery fee in case the vehicle type is Car.
     * @param city - input from the frontend
     * @return delivery fee as a float based on the business rules
     */
    public float calculateDeliveryFeeForCar(String city){
        return switch (city) {
            case "Tallinn" -> 4;
            case "Tartu" -> 3.5f;
            case "Pärnu" -> 3f;
            default -> 0;
        };
    }

    /**
     * Method calculateDeliveryFeeForScooter calculates the delivery fee in case the vehicle type is Scooter.
     * Calls out two helper methods, getAirTemperatureExtraFee and getWeatherPhenomenonExtraFee for calculating
        extra fees based on the business rules.
     * @param city - input from the frontend, needed for requesting the latest weather data for specific city
     * @return delivery fee as a float based on the business rules
     * @throws Exception, because calls out helper method getWeatherPhenomenonExtraFee, which might throw an Exception
     */
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
        System.out.println("Weather info: station: " + latestWeather.name() + ", temperature: " + latestWeather.temperature() + ", wind speed: "
        + latestWeather.windspeed() + ", phenomenon: " + latestWeather.phenomenon());
        return deliveryFee + getAirTemperatureExtraFee(latestWeather.temperature()) + getWeatherPhenomenonExtraFee(latestWeather.phenomenon());
    }

    /**
     * Method calculateDeliveryFeeForBike calculates the delivery fee in case the vehicle type is Bike.
     * Calls out three helper methods, getAirTemperatureExtraFee, getWindSpeedExtraFee and getWeatherPhenomenonExtraFee
        for calculating extra fees based on the business rules.
     * @param city - input from the frontend, needed for requesting the latest weather data for specific city
     * @return delivery fee as a float based on the business rules
     * @throws Exception, because calls out three helper methods for calculating extra fees, which might throw an Exception
     */
    public float calculateDeliveryFeeForBike(String city) throws Exception {
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
        System.out.println("Weather info: station: " + latestWeather.name() + ", temperature: " + latestWeather.temperature() + ", wind speed: "
                + latestWeather.windspeed() + ", phenomenon: " + latestWeather.phenomenon());
        return deliveryFee + getAirTemperatureExtraFee(latestWeather.temperature()) +
                getWindSpeedExtraFee(latestWeather.windspeed()) + getWeatherPhenomenonExtraFee(latestWeather.phenomenon());
    }

    /**
     * Helper method getAirTemperatureExtraFee calculates the extra fee according to given air temperature
     * @param temperature - air temperature from the latest weather info
     * @return extra fee as float based on the business rules
     */
    public float getAirTemperatureExtraFee(float temperature) {
        if (temperature < -10f) return 1f;
        else if (temperature >= -10f && temperature <= 0f) return 0.5f;
        else return 0;
    }

    /**
     * Helper method getWindSpeedExtraFee calculates the extra fee according to given wind speed
     * @param windspeed - wind speed from the latest weather info
     * @return extra fee as float based on the business rules
     * @throws Exception, if wind speed is greater than 20 m/s
     */
    public float getWindSpeedExtraFee(float windspeed) throws Exception {
        if (windspeed > 20f) throw new Exception("Usage of selected vehicle type is forbidden");
        else if (windspeed <= 20f && windspeed >= 10f) return 0.5f;
        else return 0;
    }

    /**
     * Helper method getWeatherPhenomenonExtraFee calculates the extra fee according to given weather phenomenon
     * @param phenomenon - weather phenomenon from the latest weather info
     * @return extra fee as float based on the business rules
     * @throws Exception, if weather phenomenon is either Glaze, Hail, Thunder or Thunderstorm
     */
    public float getWeatherPhenomenonExtraFee(String phenomenon) throws Exception {
        if (phenomenon.contains("snow") || phenomenon.contains("sleet")) return 1f;
        else if (phenomenon.contains("rain") || phenomenon.equals("Light shower") || phenomenon.equals("Moderate shower") ||
                phenomenon.equals("Heavy shower")) return 0.5f;
        else if (phenomenon.equals("Glaze") || phenomenon.equals("Hail") ||
                phenomenon.toLowerCase().contains("thunder")) throw new Exception("Usage of selected vehicle type is forbidden");
        else return 0;
    }
}
