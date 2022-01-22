package com.hnj.code.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonPropertyOrder({
        "countryName",
        "population",
        "exchangeRatesInIDR",
        "message"
})
public class RateExchangeResponse {
    @JsonProperty("countryName")
    private String countryName;

    @JsonProperty("population")
    private Integer population;

    @JsonProperty("exchangeRatesInIDR")
    private List<IDRExchangeRateResponse> exchangeRatesInIDR;

    @JsonProperty("message")
    private String message;
}
