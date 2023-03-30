package com.raiki.app.rest.controller;

import com.raiki.app.rest.model.Weather;
import com.raiki.app.rest.weather.WeatherService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @MockBean
    WeatherService weatherService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getWeather() throws Exception {
        Weather weather = new Weather(null,"Pärnu",123L,1.0f,2.0f,"Rain",12345L);
        List<Weather> weatherList = List.of(weather);

        Mockito.when(weatherService.getAllWeatherData()).thenReturn(weatherList);

        mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name",Matchers.is("Pärnu")));
    }
}