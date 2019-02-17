package com.jgprogram.sjug.httpclient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class ExchangeRatesUpdate {

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private Set<Rate> rates;

    public ExchangeRatesUpdate(LocalDateTime time, Set<Rate> rates) {
        this.time = time;
        this.rates = rates;
    }

    private ExchangeRatesUpdate() {
    }

    public LocalDateTime time() {
        return time;
    }

    public Set<Rate> rates() {
        return Collections.unmodifiableSet(rates);
    }

    public static class Rate {
        private String currency;
        private BigDecimal value;

        public static Rate of(String currency, BigDecimal value) {
            return new Rate(currency, value);
        }

        public Rate(String currency, BigDecimal value) {
            this.currency = currency;
            this.value = value;
        }

        private Rate() {
        }

        public String currency() {
            return currency;
        }

        public BigDecimal value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rate rate = (Rate) o;
            return currency.equals(rate.currency) &&
                    value.equals(rate.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(currency, value);
        }
    }
}
