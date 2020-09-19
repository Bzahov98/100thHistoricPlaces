package com.pmu.data.model.places;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PlaceDataService {

    Page<Place> findAll(Predicate predicate, Pageable pageable);

    Optional<Place> findOne(Predicate predicate);

    Place create(Place place);

    Place findById(UUID id);
}
