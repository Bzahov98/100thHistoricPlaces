package com.pmu.api.dto.filter;

import com.querydsl.core.types.Predicate;

public interface Filter {

    Predicate toPredicate();
}
