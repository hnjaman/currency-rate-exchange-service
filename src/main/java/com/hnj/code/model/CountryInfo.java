package com.hnj.code.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {
    private CountryName name;

    @JsonProperty("currencies")
    private Currencies currencies;

    private Integer population;
}
