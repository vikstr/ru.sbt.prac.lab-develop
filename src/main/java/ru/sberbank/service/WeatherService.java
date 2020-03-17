package ru.sberbank.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.jsonparser.Data;
import ru.sberbank.jsonparser.Hourly;
import ru.sberbank.jsonparser.Item;

import java.io.IOException;
import java.util.*;


@Component
public class WeatherService {
    final String TOKEN = "c27fb993535ea9bcd42a6a2c46d506bd";

    @Autowired
    private RestTemplate restTemplate;


    public String getResponse(String time) {
        String url = "https://api.darksky.net/forecast/" + TOKEN + "/55.3601,37.5589" + time + "?exclude=currently&units=auto";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalStateException();
        }
        return response.getBody();
    }

    public ArrayList<Double> parseResponse(String responseString) {
        String[] lines = responseString.split("\n");
        ArrayList<Double> ans = new ArrayList<>();
        for (String obj: lines) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Hourly hourly = objectMapper.readValue(obj, Hourly.class);
                Data data = hourly.getHourly();
                for (Item item: data.getData()){
                    Double temperature = item.getTemperature();
                    ans.add(temperature);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
    public Double getCurrentTemperature(){
        long time = System.currentTimeMillis() / 1000L;
        return parseResponse(getResponse("" + time)).get(0);
    }
    public List<Double> getHistory(int days){
        long time = System.currentTimeMillis() / 1000L;
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i<30; i++){
            time-= 86400;
            ans.add(parseResponse(getResponse("" + time)).get(0));
        }
        return ans;
    }
}
