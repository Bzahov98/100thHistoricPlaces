package com.pmu.service.places;

import com.pmu.api.dto.filter.ApiPlaceFilter;
import com.pmu.api.dto.filter.Filter;
import com.pmu.data.model.places.Place;
import com.pmu.data.model.places.PlaceDataService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceDataService placeDataService;

    @Override
    public Place create(Place place) {
        return placeDataService.create(place);
    }

    @Override
    public Page<Place> findAll(Filter filter, Pageable pageable) {
        return placeDataService.findAll(filter.toPredicate(), pageable);
    }

    @Override
    public Place findById(UUID id) {
        return placeDataService.findById(id);
    }
}
