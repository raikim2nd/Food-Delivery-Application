package com.raiki.app.rest.delivery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @InjectMocks
    DeliveryService deliveryService;

    @Test
    void calculateDeliveryFeeForCar() {
        float delivery = deliveryService.calculateDeliveryFeeForCar("Tallinn");
        System.out.println(delivery);
        assertEquals(4.0f,delivery);
    }

    @Test
    void getAirTemperatureExtraFee() {
        float extraFeeforAirTemperature = deliveryService.getAirTemperatureExtraFee(-11.3f);
        assertEquals(1f,extraFeeforAirTemperature);
    }

    @Test
    void getWindSpeedExtraFee() throws Exception {
        float extraFeeForWindSpeed = deliveryService.getWindSpeedExtraFee(15f);
        assertEquals(0.5f,extraFeeForWindSpeed);
    }

    @Test
    void getWeatherPhenomenonExtraFee() throws Exception {
        float extraFeeForPhenomenon = deliveryService.getWeatherPhenomenonExtraFee("Heavy snow shower");
        assertEquals(1f,extraFeeForPhenomenon);
    }

    @Test
    void getWindSpeedExtraFeeExpectedException() {
        Throwable throwable = assertThrows(Throwable.class, () -> deliveryService.getWindSpeedExtraFee(25f));
        assertEquals(Exception.class,throwable.getClass());
    }

    @Test
    void getWeatherPhenomenonExtraFeeExpectedException() {
        Throwable throwable = assertThrows(Throwable.class, () -> deliveryService.getWeatherPhenomenonExtraFee("Thunder"));
        assertEquals(Exception.class,throwable.getClass());
    }
}