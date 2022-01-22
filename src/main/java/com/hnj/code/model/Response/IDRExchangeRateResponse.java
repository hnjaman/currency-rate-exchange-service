package com.hnj.code.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IDRExchangeRateResponse {
    private String currency;
    private String currencyFullName;
    private Double exchangeRateInIDR;
}
