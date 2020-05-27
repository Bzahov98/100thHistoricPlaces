package com.pmu.api.dto.filter;

import com.pmu.data.model.places.QPlace;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPlaceFilter implements Filter {
    private String name;

    @Override
    public Predicate toPredicate(){
        QPlace qPlace = QPlace.place;

        BooleanExpression predicate = qPlace.id.isNotNull().and(qPlace.deleted.eq(false));

        if (!StringUtils.isEmpty(name)){
            predicate = predicate.and(qPlace.name.containsIgnoreCase(name));
        }

        return predicate;

    }
}
