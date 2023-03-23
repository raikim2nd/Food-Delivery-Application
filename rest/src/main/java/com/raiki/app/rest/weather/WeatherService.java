package com.raiki.app.rest.weather;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.repository.WeatherRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

// WeatherService klassis peaks olema kogu businesslogic ehk meetodid, mis on seotud database-ga jms
// siia vist vaja @Service annotationit, vaadata videot nt Spring annotations kohta
@Service // enne oli @Component
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    private List<Station> stations;

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
}
