package com.raiki.app.rest.repository;

import com.raiki.app.rest.model.Weather;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

// CrudRepository has builtin methods and this way you don't have to write sql
public interface WeatherRepository extends CrudRepository<Weather, Integer> {

}
