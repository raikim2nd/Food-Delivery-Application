package com.raiki.app.rest.weather;

import jakarta.xml.bind.annotation.*;

/**
 * Class for reading and storing data from the weather portal
 */
@XmlRootElement(name = "station")
public class Station {

    private String name;
    private Long wmocode;
    private Float airtemperature;
    private Float windspeed;
    private String phenomenon;
    private Long timestamp;


    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "wmocode")
    public Long getWmocode() {
        return wmocode;
    }

    public void setWmocode(Long wmocode) {
        this.wmocode = wmocode;
    }

    @XmlElement(name = "airtemperature")
    public Float getAirtemperature() {
        return airtemperature;
    }

    public void setAirtemperature(Float airtemperature) {
        this.airtemperature = airtemperature;
    }

    @XmlElement(name = "windspeed")
    public Float getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Float windspeed) {
        this.windspeed = windspeed;
    }

    @XmlElement(name = "phenomenon")
    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
