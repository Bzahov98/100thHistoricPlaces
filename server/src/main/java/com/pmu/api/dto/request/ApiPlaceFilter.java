package com.pmu.api.dto.request;

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
public class ApiPlaceFilter {
    private String name;

    public Predicate toPredicate(){
        QPlace qPlace = QPlace.place;

        BooleanExpression predicate = qPlace.deleted.eq(false);

        if (StringUtils.isEmpty(name)){
            predicate = predicate.and(qPlace.name.likeIgnoreCase(name));
        }

        return predicate;

    }
}
