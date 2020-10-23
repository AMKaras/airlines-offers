package com.github.amkaras.airlines.offers.dao;

import com.github.amkaras.airlines.offers.model.Location;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class LocationDaoImpl implements LocationDao {

    private final Set<Location> supportedLocations = new HashSet<>();

    @PostConstruct
    private void addLocations() {
        Stream.of(
                new Location("Warsaw", 52.229676, 21.012229),
                new Location("Cracow", 50.049683, 19.944544),
                new Location("Rome", 41.902782, 12.496366),
                new Location("Paris", 48.864716, 2.349014),
                new Location("London", 51.509865, -0.118092),
                new Location("Moscow", 55.751244, 37.618423),
                new Location("Ankara", 39.925533, 32.866287),
                new Location("Washington", 47.751076, -120.740135),
                new Location("Sydney", -33.865143, 151.209900),
                new Location("Tokio", 35.652832, 139.839478))
                .forEach(supportedLocations::add);
    }

    @Override
    public Set<Location> findAll() {
        return supportedLocations;
    }

    @Override
    public Optional<Location> findByName(String name) {
        return supportedLocations.stream()
                .filter(location -> name.equals(location.getName()))
                .findFirst();
    }
}
