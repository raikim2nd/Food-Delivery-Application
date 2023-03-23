package com.raiki.app.rest.weather;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
public class Observations {
    private Long timestamp;
    private List<Station> stationList;

    @XmlAttribute
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @XmlElement(name = "station")
    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }
}
