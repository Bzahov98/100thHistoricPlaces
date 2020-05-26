package com.pmu.service.places;

import com.pmu.api.dto.request.ApiPlaceFilter;
import com.pmu.data.model.places.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PlaceService {

    Place create(Place place);

    Page<Place> findAll(ApiPlaceFilter filter, Pageable pageable);

    Place findById(UUID id);

}
