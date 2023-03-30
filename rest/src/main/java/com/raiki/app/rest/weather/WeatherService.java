package com.raiki.app.rest.weather;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.repository.WeatherRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    private List<Station> stations;

    /**
     * Method getNeededStations reads in the data from the weather portal and stores the data for three needed stations
        with the required fields as Station objects in a list. Then the method calls out insertWeatherToDatabase method.
     */
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

    /**
     * insertWeatherToDatabase method adds the weather data to database.
     * @param stations - list with three needed stations as Station objects
     */
    public void insertWeatherToDatabase(List<Station> stations) {
        weatherRepository.saveAll(stations.stream().map((station -> new Weather(null, station.getName(), station.getWmocode(), station.getAirtemperature(), station.getWindspeed(), station.getPhenomenon(), station.getTimestamp()))).toList());
    }

    /**
     * getAllWeatherData method receives all the weather data from the database.
     * @return list containing Weather objects for all the weather data stored in the database.
     */
    public List<Weather> getAllWeatherData() {
        System.out.println("Get all weather data");
        return (List<Weather>) weatherRepository.findAll();
    }
}
