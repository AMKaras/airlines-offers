package com.github.amkaras.airlines.offers.controller;

import com.github.amkaras.airlines.offers.model.FlightDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@RestController
public class FlightPricesController {

    @RequestMapping("/flights")
    public List<FlightDetails> flights() {
        List<FlightDetails> details = new ArrayList<>();
        details.add(new FlightDetails("Cracow", "Rome",
                ZonedDateTime.now(), ZonedDateTime.now().plus(2, HOURS),
                BigDecimal.valueOf(100), "USD"));
        details.add(new FlightDetails("Cracow", "Moscow",
                ZonedDateTime.now().plus(1, HOURS), ZonedDateTime.now().plus(4, HOURS),
                BigDecimal.valueOf(150), "USD"));
        return details;
    }
}
