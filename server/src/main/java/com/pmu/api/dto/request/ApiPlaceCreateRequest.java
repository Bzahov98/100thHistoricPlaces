package com.pmu.api.dto.request;

import com.pmu.data.model.places.LatLng;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPlaceCreateRequest {

    private String name;

    private String description;

    private LatLng latLng;
}
