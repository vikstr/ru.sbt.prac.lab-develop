package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.dao.entity.Rate;
import ru.sberbank.dao.repository.RateCrudRepository;

import javax.validation.constraints.Null;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class RBKServiceImpl{

    private Optional<Rate> rate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired private RestTemplate restTemplate;
    @Autowired private RateCrudRepository rateCrudRepository;

    @Transactional
    public void saveRate(Double rate) {
        Date date = Calendar.getInstance().getTime();
        System.out.println(date);
        rateCrudRepository.save(new Rate(rate, date));
    }

    @Transactional
    public Optional<Double> getRateByDate(Date date) {
        String dateString = Rate.dateFormat(date);
        Optional<Rate> rate = rateCrudRepository.findByDate(dateString);
        return rate.map(Rate::getRate);
    }

    public double getMaxRateForLastMonth() {
        Optional<Double> maxRate = getRateByDate(Calendar.getInstance().getTime());
        if (maxRate.isPresent()) {
            return maxRate.get();
        }
        Double rate = getMaxFromArray(parseResponse(getResponse("30")));
        saveRate(rate);
        return rate;
    }

    public String getResponse(String days) {
        String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";
        ResponseEntity<String> response = restTemplate.getForEntity(url + days, String.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalStateException();
        }
        return response.getBody();
    }

    public ArrayList<Double> parseResponse(String responseString) {
        String[] lines = responseString.split("\n");

        ArrayList<Double> ans = new ArrayList<>();

        for (String line : lines) {
            String[] elements = line.split(",");
            ans.add(Double.parseDouble(elements[elements.length - 1]));
        }
        return ans;
    }

    public Double getMaxFromArray(List<Double> doubleList) {
        double max = Double.MIN_VALUE;
        for (Double d : doubleList) {
            max = Math.max(max, d);
        }
        return max;
    }
    public List<Double> getHistory(String days){
        return parseResponse(getResponse(days));
    }

}