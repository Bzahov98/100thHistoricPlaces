package com.pmu.data.model.places;

import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceDataServiceImpl implements PlaceDataService {

    private PlaceRepository placeRepository;

    @Override
    public Page<Place> findAll(Predicate predicate, Pageable pageable) {
        return placeRepository.findAll(predicate, pageable);
    }

    @Override
    public Place create(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public Place findById(UUID id) {
        return placeRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.PLACE_NOT_FOUND));
    }
}
