package ru.sberbank.controller;


import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.jsonparser.Predict;
import ru.sberbank.jsonparser.RBK;
import ru.sberbank.jsonparser.Weather;
import ru.sberbank.service.Prediction;
import ru.sberbank.service.RBKServiceImpl;
import ru.sberbank.service.WeatherService;

import java.util.List;


@RestController
@Component
public class ViewController {
    @Autowired
    private RBKServiceImpl service;
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private Prediction prediction;
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public Weather indexweather() {
        Weather weather = new Weather();
        weather.temperature = weatherService.getCurrentTemperature();
        return weather;
    }
    @RequestMapping(value = "/rbk", method = RequestMethod.GET)
    public RBK index() {
        RBK rbk = new RBK();
        rbk.rate = service.getMaxRateForLastMonth();
        return rbk;
    }

    @RequestMapping(value = "/prediction", method = RequestMethod.GET)
    @ResponseBody
    public Predict indexprediction() {
        Predict predict = new Predict();
        predict.prediction = prediction.predict();
        return predict;
    }
}
