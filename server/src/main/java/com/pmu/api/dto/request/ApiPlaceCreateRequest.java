package com.pmu.api.dto.request;

import com.pmu.data.model.places.LatLng;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPlaceCreateRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Valid
    private LatLng latLng;

    @NotNull
    private Integer points;
}
