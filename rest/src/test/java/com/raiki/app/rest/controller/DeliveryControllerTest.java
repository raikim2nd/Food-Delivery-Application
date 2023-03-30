package com.raiki.app.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raiki.app.rest.delivery.DeliveryData;
import com.raiki.app.rest.delivery.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @MockBean
    DeliveryService deliveryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculateDeliveryFeeRequestOk() throws Exception {
        DeliveryData deliveryData = new DeliveryData("Tallinn", "Car");
        float deliveryFee = deliveryService.calculateDeliveryFeeForCar(deliveryData.getCity());

        Mockito.when(deliveryFee).thenReturn(4f);

        mockMvc.perform(post("/delivery")
                        .content(objectMapper.writeValueAsString(deliveryData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}