package com.pmu.data.model.places;


import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LatLng {

    @NonNull
    private Double longitude;

    @NotNull
    private Double latitude;

}
