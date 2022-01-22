package com.hnj.code.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "idr_exchange_rate")
public class ExchangeRateToIDR {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @Column(name = "currency_full_name")
    private String currencyFullName;

    @NotNull
    @Column(name = "exchange_rate_in_idr")
    private Double exchangeRateInIDR;

    @NotNull
    @Column(name = "requested_at")
    private Date requestedAt;
}

