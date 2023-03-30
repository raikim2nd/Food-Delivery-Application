package com.raiki.app.rest.repository;

import com.raiki.app.rest.model.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {

}
