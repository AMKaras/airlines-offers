package com.github.amkaras.airlines.offers.service;

import com.github.amkaras.airlines.offers.model.FlightDetails;

import java.time.ZonedDateTime;
import java.util.List;

public interface FlightsPricesService {

    List<FlightDetails> findFlightsFor(String origin, String destination, ZonedDateTime from, ZonedDateTime to);
}
