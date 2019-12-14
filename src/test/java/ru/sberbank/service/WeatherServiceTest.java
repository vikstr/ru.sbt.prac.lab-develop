package ru.sberbank.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {
    private String responseBody = "Empty";
    @InjectMocks
    private WeatherService weatherservice = new WeatherService();

    @Before
    public void setUp() throws Exception {
        try {
            responseBody = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/ru/sberbank/resources/weather.json").toURI())));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Mockito.when(weatherservice.getResponse(any())).thenReturn(responseBody);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseResponse() {
        ArrayList<Double> weatherResponse = weatherservice.parseResponse(responseBody);
        assertEquals(-0.2, weatherResponse.get(0), 0.01);
    }


    @Test
    public void getCurrentWeather() {
        Double weatherCurrent = weatherservice.getCurrentTemperature();
        assertEquals(3, weatherCurrent, 2);
    }
}