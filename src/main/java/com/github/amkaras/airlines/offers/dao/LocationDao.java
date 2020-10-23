package com.github.amkaras.airlines.offers.dao;

import com.github.amkaras.airlines.offers.model.Location;

import java.util.Optional;
import java.util.Set;

public interface LocationDao {

    Set<Location> findAll();

    Optional<Location> findByName(String name);
}
