package com.raiki.app.rest.model;

import org.springframework.data.annotation.Id;

public record Weather(@Id Integer id, String name, Long wmocode, Float temperature, Float windspeed,
                      String phenomenon, Long timestamp) {
}
