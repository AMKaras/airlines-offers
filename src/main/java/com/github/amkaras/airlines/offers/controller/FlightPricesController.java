package com.github.amkaras.airlines.offers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightPricesController {

    @RequestMapping("/flights")
    public String flights(){
        return "dupa";
    }

}
