package com.github.amkaras.airlines.offers.service;

import com.github.amkaras.airlines.offers.dao.LocationDao;
import com.github.amkaras.airlines.offers.model.FlightDetails;
import com.github.amkaras.airlines.offers.model.Location;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static com.javadocmd.simplelatlng.LatLngTool.distance;

@Service
public class FlightsPricesServiceImpl implements FlightsPricesService {

    public static final double MAX_DISTANCE_KILOMETERS = 17_000;
    public static final int MAX_FLIGHT_HOURS = 20;
    public static final BigDecimal MAX_PRICE_USD = BigDecimal.valueOf(3_000);
    public static final String USD = "USD";

    private final LocationDao locationDao;

    public FlightsPricesServiceImpl(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public List<FlightDetails> findFlightsFor(String origin, String destination,
                                              ZonedDateTime from, ZonedDateTime to) {
        try {
            Location originLocation = locationDao.findByName(origin)
                    .orElseThrow(locationNotFoundException(origin));
            Location destinationLocation = locationDao.findByName(destination)
                    .orElseThrow(locationNotFoundException(destination));
            return estimatePrices(originLocation, destinationLocation, from, to);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<FlightDetails> estimatePrices(Location origin, Location destination,
                                               ZonedDateTime from, ZonedDateTime to) {
        double distance = distance(new LatLng(origin.getLatitude(), origin.getLongitude()),
                new LatLng(destination.getLatitude(), destination.getLongitude()), LengthUnit.KILOMETER);
        int hoursOfFlight = (int) ((distance / MAX_DISTANCE_KILOMETERS) * MAX_FLIGHT_HOURS + 0.9);
        double randomDistanceFactor = (distance / MAX_DISTANCE_KILOMETERS)
                * ThreadLocalRandom.current().nextDouble(0.5, 1.0)
                * ThreadLocalRandom.current().nextDouble(0.5, 1.0);
        BigDecimal estimatedPrice = MAX_PRICE_USD.multiply(BigDecimal.valueOf(randomDistanceFactor));
        return betweenDatesWithEstimatedPrice(estimatedPrice, hoursOfFlight,
                origin.getName(), destination.getName(), from, to);
    }

    private List<FlightDetails> betweenDatesWithEstimatedPrice(BigDecimal estimatedPrice, int hoursOfFlight,
                                                               String origin, String destination,
                                                               ZonedDateTime from, ZonedDateTime to) {
        List<FlightDetails> flightDetails = new ArrayList<>();
        while (from.isBefore(to.plusDays(1))) {
            if (ThreadLocalRandom.current().nextDouble(0.0, 1.0) >= 0.1) {
                long additionalHours = ThreadLocalRandom.current().nextLong(0, 12);
                flightDetails.add(new FlightDetails(origin, destination,
                        from.plusHours(additionalHours), from.plusHours(hoursOfFlight).plusHours(additionalHours),
                        estimatedPrice.setScale(2, RoundingMode.HALF_UP), USD));
            }
            from = from.plusDays(1);
        }
        return flightDetails;
    }

    private Supplier<IllegalArgumentException> locationNotFoundException(String location) {
        return () -> new IllegalArgumentException("Cannot find location for name " + location);
    }
}
