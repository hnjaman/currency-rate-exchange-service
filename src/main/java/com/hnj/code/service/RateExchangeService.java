package com.hnj.code.service;


import com.hnj.code.model.Response.RateExchangeResponse;

public interface RateExchangeService {
    RateExchangeResponse getExchangeRate(String countryName, String userEmail);
}
