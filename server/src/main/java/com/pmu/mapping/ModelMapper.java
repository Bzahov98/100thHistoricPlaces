package com.pmu.mapping;

import com.pmu.api.dto.response.ApiPlaceCheckResponse;
import com.pmu.data.model.places.UserDetailPlaceAssignment;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory mapperFactory) {
        super.configure(mapperFactory);

        mapperFactory.registerClassMap(mapperFactory.classMap(UserDetailPlaceAssignment.class, ApiPlaceCheckResponse.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(UserDetailPlaceAssignment userDetailPlaceAssignment, ApiPlaceCheckResponse apiPlaceCheckResponse, MappingContext context) {
                        apiPlaceCheckResponse.setCheckDate(userDetailPlaceAssignment.getCreatedDate());
                        apiPlaceCheckResponse.setPlaceId(userDetailPlaceAssignment.getPlace().getId());
                    }
                }));
    }
}
