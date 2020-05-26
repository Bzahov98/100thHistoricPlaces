package com.pmu.data.model.places;

import com.pmu.util.LatLngConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Place {

    @Id
    private UUID id;

    private String name;

    private String description;

    @Convert(converter = LatLngConverter.class)
    private LatLng latLng;

    private Boolean deleted = false;
}
