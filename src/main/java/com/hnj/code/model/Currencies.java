package com.hnj.code.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Currencies {
    private Map<String, String> currency = new HashMap<>();

    @JsonAnySetter
    public void dataProcess(String name, LinkedHashMap<String, String> value) {
        currency.put(name, value.get("name"));
    }
}
