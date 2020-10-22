package com.github.amkaras.airlines.offers.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class FlightDetails {

    private final String origin;
    private final String destination;
    private final ZonedDateTime departure;
    private final ZonedDateTime arrival;
    private final BigDecimal price;
    private final String currency;

    public FlightDetails(String origin, String destination,
                         ZonedDateTime departure, ZonedDateTime arrival,
                         BigDecimal price, String currency) {
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.currency = currency;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public ZonedDateTime getDeparture() {
        return departure;
    }

    public ZonedDateTime getArrival() {
        return arrival;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
