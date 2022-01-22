package com.hnj.code.controller;

import com.hnj.code.config.JwtTokenUtil;
import com.hnj.code.model.AuthenticatedUserInfo;
import com.hnj.code.model.Response.RateExchangeResponse;
import com.hnj.code.service.RateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class RateExchangeController {

    private RateExchangeService rateExchangeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public RateExchangeController(RateExchangeService rateExchangeService) {
        this.rateExchangeService = rateExchangeService;
    }

    @GetMapping("/idr/rate/exchange/{countryName}")
	public RateExchangeResponse getMaps(@PathVariable("countryName") String countryName, HttpServletRequest request){
        AuthenticatedUserInfo authenticatedUserInfo = jwtTokenUtil.getAuthUserInfo(request);
        if(authenticatedUserInfo.isAuthenticated()){
            return rateExchangeService.getExchangeRate(countryName, authenticatedUserInfo.getEmail());
        } else {
            return RateExchangeResponse.builder()
                    .countryName(null)
                    .population(null)
                    .exchangeRatesInIDR(null)
                    .message(authenticatedUserInfo.getMessage())
                    .build();
        }
    }
}
