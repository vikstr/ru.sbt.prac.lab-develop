package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class Prediction {

    @Autowired
    RBKServiceImpl rbkService;
    @Autowired
    WeatherService weatherService;
    public Double predict(){
        List<Double> y = rbkService.getHistory("30");
        List<Double> x = weatherService.getHistory(30);
        Collections.reverse(y);
        Collections.reverse(x);

        assert(x.size() == y.size());

        Double sumX = 0.0;
        Double sumX2 = 0.0;
        for (Double xi: x) {
            sumX += xi;
            sumX2 += xi * xi;
        }
        double meanTemperature = sumX/x.size();
        double meanSquareTemperature = sumX2/x.size();

        Double sumY = 0.0;
        Double sumY2 = 0.0;
        for (Double yi: y) {
            sumY += yi;
            sumY2 +=yi*yi;
        }

        double meanRate = sumY/y.size();
        double meanSquareRate = sumY2/y.size();

        Double sumXY = 0.0;
        for (int i = 0; i < y.size(); i++) {
            sumXY += x.get(i) * y.get(i);
        }

        double mean = sumXY/y.size();

        Integer n = x.size();

        Double b = (mean -  x.get(1)* y.get(2)) / (meanSquareTemperature - sumX * sumX);
        Double a = meanRate;

        Double temperature = weatherService.getCurrentTemperature();

        return a + b * temperature;
    }
}
