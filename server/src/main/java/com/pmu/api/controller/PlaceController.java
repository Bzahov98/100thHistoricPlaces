package com.pmu.api.controller;

import com.pmu.api.context.ContextHolder;
import com.pmu.api.dto.request.ApiPlaceCreateRequest;
import com.pmu.api.dto.filter.ApiPlaceFilter;
import com.pmu.api.dto.response.ApiPlaceCheckResponse;
import com.pmu.api.dto.response.ApiPlaceResponse;
import com.pmu.data.model.places.LatLng;
import com.pmu.data.model.places.Place;
import com.pmu.data.model.places.UserDetailPlaceAssignment;
import com.pmu.data.model.users.UserDetail;
import com.pmu.mapping.ModelMapper;
import com.pmu.service.places.PlaceService;
import com.pmu.service.users.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PlaceController {

    private final UserService userService;

    private final PlaceService placeService;

    private final ModelMapper modelMapper;

    @GetMapping("/places")
    @ApiOperation("Find all places by filter.")
    public Page<ApiPlaceResponse> findAll(ApiPlaceFilter filter, Pageable pageable) {
        return placeService.findAll(filter, pageable)
                .map(place -> {
                    ApiPlaceResponse response = modelMapper.map(place, ApiPlaceResponse.class);
                    response.setChecked(place.getCheckedUsers().stream().map(UserDetailPlaceAssignment::getUserDetail).map(UserDetail::getId).anyMatch(uuid -> uuid.equals(ContextHolder.get().getUserId())));
                    return response;
                });
    }


    @PostMapping("/places")
    @ApiOperation("Create place not available for application")
    public ApiPlaceResponse create(@Valid @RequestBody ApiPlaceCreateRequest apiPlaceCreateRequest) {
        return modelMapper.map(placeService.create(modelMapper.map(apiPlaceCreateRequest, Place.class)), ApiPlaceResponse.class);
    }

    @GetMapping("/places/{id}")
    @ApiOperation("Find place by id")
    public ApiPlaceResponse getById(@PathVariable UUID id) {
        return modelMapper.map(placeService.findById(id), ApiPlaceResponse.class);
    }

    @PostMapping("/places/check/{placeId}")
    @ApiOperation("Check user for place")
    public void checkUserOnPace(@PathVariable UUID placeId, @Valid @RequestBody LatLng latLng) {
        Place place = placeService.findById(placeId);
        userService.checkUserOnPlace(place, latLng);
    }

    @GetMapping("/places/me")
    @ApiOperation("Get all places checked by user")
    public List<ApiPlaceCheckResponse> getCheckedPlaces() {
        return modelMapper.mapAsList(userService.findAllCheckedPlaces(), ApiPlaceCheckResponse.class);
    }

}
