package com.pmu.mapping;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        super.configure(factory);
    }
}
