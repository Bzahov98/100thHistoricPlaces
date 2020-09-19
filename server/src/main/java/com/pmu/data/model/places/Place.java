package com.pmu.data.model.places;

import com.pmu.util.LatLngConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Place {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    @Convert(converter = LatLngConverter.class)
    private LatLng latLng;

    private Boolean deleted = false;

    private Integer points = 0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "place")
    private List<UserDetailPlaceAssignment> checkedUsers;

}
