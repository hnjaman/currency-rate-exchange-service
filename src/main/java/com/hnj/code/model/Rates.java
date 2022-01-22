package com.hnj.code.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Rates {
    private Map<String, Double> ratesMap = new HashMap<>();

    @JsonAnySetter
    public void rateProcess(String name, Double value) {
        ratesMap.put(name, value);
    }
}
