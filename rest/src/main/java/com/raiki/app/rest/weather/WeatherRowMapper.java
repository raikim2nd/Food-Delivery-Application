package com.raiki.app.rest.weather;

import com.raiki.app.rest.model.Weather;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherRowMapper implements RowMapper<Weather> {

    @Override
    public Weather mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Weather(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getLong("wmocode"),
                rs.getFloat("temperature"),
                rs.getFloat("windspeed"),
                rs.getString("phenomenon"),
                rs.getLong("timestamp")
        );
    }
}
