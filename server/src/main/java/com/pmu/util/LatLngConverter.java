package com.pmu.util;

import com.pmu.data.model.places.LatLng;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LatLngConverter implements AttributeConverter<LatLng, String> {
    @Override
    public String convertToDatabaseColumn(LatLng latLng) {
        return JsonUtils.serialize(latLng);
    }

    @Override
    public LatLng convertToEntityAttribute(String s) {
        return JsonUtils.deserialize(s, LatLng.class);
    }
}
