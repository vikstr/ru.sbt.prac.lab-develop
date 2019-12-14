package ru.sberbank.jsonparser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    Double temperature;
    public Double getTemperature() {return this.temperature;}
}
