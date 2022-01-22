package com.hnj.code.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnj.code.model.CountryInfo;
import com.hnj.code.model.Response.IDRExchangeRateResponse;
import com.hnj.code.model.Response.RateExchangeResponse;
import com.hnj.code.service.RateExchangeService;
import com.hnj.code.model.ExchangeRate;
import com.hnj.code.model.ExchangeRateToIDR;
import com.hnj.code.repository.RateExchangeRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RateExchangeServiceImpl implements RateExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateExchangeServiceImpl.class);

    private final RateExchangeRepository rateExchangeRepository;

    @Autowired
    public RateExchangeServiceImpl(RateExchangeRepository rateExchangeRepository) {
        this.rateExchangeRepository = rateExchangeRepository;
    }

    private static final String IDR_CURRENCY = "IDR";
    private static final String SUCCESS = "Success";

    @Override
    public RateExchangeResponse getExchangeRate(String countryName, String userEmail) {
        RateExchangeResponse rateExchangeResponse = new RateExchangeResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://restcountries.com/v3.1/name/" + countryName);
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getEntity() == null) {
                LOGGER.error(countryName.toUpperCase() + " country information not found");
                throw new RuntimeException(countryName.toUpperCase() + " country information not found");
            }

            InputStream inputStream = response.getEntity().getContent();
            String jsonString = IOUtils.toString(inputStream);
            inputStream.close();

            CountryInfo countryInfo;

            if (!jsonString.contains("{\"status\":404,\"message\":\"Not Found\"}")) {
                countryInfo = new ObjectMapper().readValue(jsonString.substring(1, jsonString.length() - 1), CountryInfo.class);
                rateExchangeResponse.setCountryName(countryInfo.getName().getCommon());
                rateExchangeResponse.setPopulation(countryInfo.getPopulation());

                if (countryInfo.getCurrencies() != null) {
                    return getExchangeRateInIDR(countryInfo, rateExchangeResponse, userEmail);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Exception in getExchangeRate for ", countryName, e);
        }
        return rateExchangeResponse;
    }

    private RateExchangeResponse getExchangeRateInIDR(CountryInfo countryInfo, RateExchangeResponse rateExchangeResponse, String userEmail) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://data.fixer.io/api/latest?access_key=794bdc56065d1ff19dc7bcbab2bebdbb");
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() == null) {
                LOGGER.error("fixer.io Current currency exchange information not found");
                throw new RuntimeException("fixer.io Current currency exchange information not found");
            }

            InputStream inputStream2 = response.getEntity().getContent();
            String exchangeRateJsonString = IOUtils.toString(inputStream2);
            inputStream2.close();

            ExchangeRate exchangeRate = new ObjectMapper().readValue(exchangeRateJsonString, ExchangeRate.class);

            List<IDRExchangeRateResponse> exchangeRateToIDRS = new ArrayList<>();

            for (Map.Entry<String, String> c : countryInfo.getCurrencies().getCurrency().entrySet()) {
                Double exchangeIntoIDR = exchangeRate.getRates().getRatesMap().get(IDR_CURRENCY) / exchangeRate.getRates().getRatesMap().get(c.getKey());
                ExchangeRateToIDR exchangeRateToIDR = ExchangeRateToIDR.builder()
                        .userEmail(userEmail)
                        .currency(c.getKey())
                        .currencyFullName(c.getValue())
                        .exchangeRateInIDR(exchangeIntoIDR)
                        .requestedAt(new Date())
                        .build();

                rateExchangeRepository.save(exchangeRateToIDR);

                exchangeRateToIDRS.add(IDRExchangeRateResponse.builder()
                        .currency(exchangeRateToIDR.getCurrency())
                        .currencyFullName(exchangeRateToIDR.getCurrencyFullName())
                        .exchangeRateInIDR(exchangeRateToIDR.getExchangeRateInIDR())
                        .build());
            }
            rateExchangeResponse.setExchangeRatesInIDR(exchangeRateToIDRS);
            rateExchangeResponse.setMessage(SUCCESS);
        } catch (IOException e) {
            LOGGER.error("Exception in getExchangeRateInIDR for ", countryInfo, e);
        }
        return rateExchangeResponse;
    }
}
