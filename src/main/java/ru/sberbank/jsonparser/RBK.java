package ru.sberbank.jsonparser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RBK {
    public Double rate;
}
