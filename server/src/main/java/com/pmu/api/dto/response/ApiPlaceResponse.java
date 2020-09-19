package com.pmu.api.dto.response;

import com.pmu.data.model.places.LatLng;
import lombok.Data;

import java.util.UUID;

@Data
public class ApiPlaceResponse {
    private UUID id;

    private String name;

    private String description;

    private LatLng latLng;

    private Boolean checked;
}
