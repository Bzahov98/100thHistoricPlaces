package com.pmu.service.places;

import com.pmu.api.dto.filter.Filter;
import com.pmu.data.model.places.LatLng;
import com.pmu.data.model.places.Place;
import com.pmu.data.model.places.PlaceDataService;
import com.pmu.data.model.places.QPlace;
import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceDataService placeDataService;

    @Override
    public Place create(Place place) {

        QPlace place1 = QPlace.place;
        BooleanExpression predicate = place1.name.eq(place.getName())
                .or(place1.latLng.eq(place.getLatLng()));
        placeDataService.findOne(predicate).ifPresent(place2 -> {
            throw new BaseException(ErrorCode.PLACE_ALREADY_EXIST, null);
        });

        return placeDataService.create(place);
    }

    @Override
    public Page<Place> findAll(Filter filter, LatLng latLng, Pageable pageable) {
        List<Place> all = placeDataService.findAll(filter.toPredicate());

        if (Objects.nonNull(latLng))
            all.sort(Comparator.comparingDouble(place -> place.getDistance(latLng)));

        return new PageImpl(all.subList(pageable.getPageNumber() * pageable.getPageSize(),
                (pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize()) > all.size() ? all.size() : pageable.getPageNumber() * pageable.getPageSize() + pageable.getPageSize()), pageable, all.size());

    }

    @Override
    public Place findById(UUID id) {
        return placeDataService.findById(id);
    }
}
