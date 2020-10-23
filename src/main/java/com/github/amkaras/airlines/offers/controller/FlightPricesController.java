package com.github.amkaras.airlines.offers.controller;

import com.github.amkaras.airlines.offers.model.FlightDetails;
import com.github.amkaras.airlines.offers.service.FlightsPricesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class FlightPricesController {

    private final FlightsPricesService flightsPricesService;

    public FlightPricesController(FlightsPricesService flightsPricesService) {
        this.flightsPricesService = flightsPricesService;
    }

    // from and to dates have to be provided as yyyy-mm-dd
    @GetMapping("/flights/{origin}/{destination}/{from}/{to}")
    public List<FlightDetails> flights(@PathVariable String origin, @PathVariable String destination,
                                       @PathVariable String from, @PathVariable String to) {
        return flightsPricesService.findFlightsFor(origin, destination,
                fromIntArray(parseDate(from)), fromIntArray(parseDate(to)));
    }

    private ZonedDateTime fromIntArray(int[] array) {
        return ZonedDateTime.of(array[0], array[1], array[2], 0, 0, 0, 0, ZoneId.of("UTC"));
    }

    private int[] parseDate(String date) {
        return Arrays.stream(date.split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
