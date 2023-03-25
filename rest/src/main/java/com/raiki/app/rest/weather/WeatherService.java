package com.raiki.app.rest.weather;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.repository.WeatherRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

// WeatherService klassis peaks olema kogu businesslogic ehk meetodid, mis on seotud database-ga jms
// siia vist vaja @Service annotationit, vaadata videot nt Spring annotations kohta
@Service // enne oli @Component
//@JavaScript("frontend://src/components/Selections.js")
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private List<Station> stations;

    public WeatherService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void getNeededStations() {

        try {
            URL uri;
            try {
                uri = new URL("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Observations observations = (Observations) jaxbUnmarshaller.unmarshal(uri);

            stations = observations.getStationList();

            // remove unnecessary stations and add timestamp from observations
            stations = stations.stream().filter(station -> {
                station.setTimestamp(observations.getTimestamp());
                return station.getName().equals("Tallinn-Harku") || station.getName().equals("Tartu-Tõravere") || station.getName().equals("Pärnu");}).toList();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        // We have gathered all the needed stations from the URL, lets add them to database
        insertWeatherToDatabase(stations);
    }

    private void insertWeatherToDatabase(List<Station> stations) {
        // streamiga ühe reana kirja panna
        weatherRepository.saveAll(stations.stream().map((station -> new Weather(null, station.getName(), station.getWmocode(), station.getAirtemperature(), station.getWindspeed(), station.getPhenomenon(), station.getTimestamp()))).toList());
    }

    public List<Weather> getAllWeatherData() {
        System.out.println("GetAllWeatherData");
        List<Weather> result = (List<Weather>) weatherRepository.findAll();
        return result;
        /*
        // Kui mingi error tühja listiga, siis:
        if (result.size() > 0) return result;
        else return new ArrayList<WeatherEntity>();
         */
    }

    public Weather getLatestWeatherForTallinn() {
        // select * from weather where name='Tallinn-Harku' order by timestamp DESC limit 1
        String sql = "select * from weather where name='Tallinn-Harku' order by timestamp DESC limit 1";
        return jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
    }

    // return string because of possibility of error message
    public String calculateDeliveryFee(String city, String vehicle) {
        String sql;
        Weather latestWeather = null;
        float deliveryFee = 0;
        //Weather latestWeather = jdbcTemplate.query(sql,weatherRepository.);
        switch (city) {
            case "Tallinn":
                sql = "select * from weather where name='Tallinn-Harku' order by timestamp DESC limit 1";
                latestWeather = jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
                System.out.println("city: " + city + "; vehicle: " + vehicle + ";temperature: " + latestWeather.temperature() + " and phenomenon: " + latestWeather.phenomenon() + " and windspeed: " + latestWeather.windspeed());
                // regional base fee
                switch (vehicle) {
                    case "Car":
                        deliveryFee += 4f;
                        break;
                    case "Scooter":
                        deliveryFee += 3.5f;
                        break;
                    case "Bike":
                        deliveryFee += 3f;
                        break;
                }
                break;
            case "Tartu":
                sql = "select * from weather where name='Tartu-Tõravere' order by timestamp DESC limit 1";
                latestWeather = jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
                System.out.println("city: " + city + "; vehicle: " + vehicle + ";temperature: " + latestWeather.temperature() + " and phenomenon: " + latestWeather.phenomenon() + " and windspeed: " + latestWeather.windspeed());
                // regional base fee
                switch (vehicle) {
                    case "Car":
                        deliveryFee += 3.5f;
                        break;
                    case "Scooter":
                        deliveryFee += 3f;
                        break;
                    case "Bike":
                        deliveryFee += 2.5f;
                        break;
                }
                break;
            case "Pärnu":
                sql = "select * from weather where name='Pärnu' order by timestamp DESC limit 1";
                latestWeather = jdbcTemplate.query(sql, new WeatherRowMapper()).get(0);
                System.out.println("city: " + city + "; vehicle: " + vehicle + ";temperature: " + latestWeather.temperature() + " and phenomenon: " + latestWeather.phenomenon() + " and windspeed: " + latestWeather.windspeed());
                // regional base fee
                switch (vehicle) {
                    case "Car":
                        deliveryFee += 3f;
                        break;
                    case "Scooter":
                        deliveryFee += 2.5f;
                        break;
                    case "Bike":
                        deliveryFee += 2f;
                        break;
                }
                break;
        }
        System.out.println("Delivery in: " + city + " and vehicle type: " + vehicle + " and base delivery fee: " + deliveryFee);
        // extra fees for weather conditions
        if (!vehicle.equals("Car")) {
            // extra fee for temperature
            if (latestWeather.temperature() < -10f) deliveryFee += 1f;
            else if (latestWeather.temperature() >= -10f && latestWeather.temperature() <= 0f) deliveryFee += 0.5f;
            // extra fee for weather phenomenon
            if (latestWeather.phenomenon().contains("snow") || latestWeather.phenomenon().contains("sleet")) deliveryFee += 1f;
            else if (latestWeather.phenomenon().contains("rain")) deliveryFee += 0.5f;
            else if (latestWeather.phenomenon().equals("Glaze") || latestWeather.phenomenon().equals("Hail") || latestWeather.phenomenon().toLowerCase().contains("thunder")) return "Usage of selected vehicle type is forbidden";
            // extra fee for wind speed
            if (vehicle.equals("Bike")) {
                if (latestWeather.windspeed() >= 10f && latestWeather.windspeed() <= 20f) deliveryFee += 0.5f;
                else if (latestWeather.windspeed() > 20f) return "Usage of selected vehicle type is forbidden";
            }
        }
        System.out.println("temperature: " + latestWeather.temperature() + " and phenomenon: " + latestWeather.phenomenon() + " and windspeed: " + latestWeather.windspeed() + " and final delivery fee:" + deliveryFee);
        return deliveryFee+"";
    }
}
