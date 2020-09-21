package com.pmu.api.dto.filter;

import com.pmu.data.model.places.QPlace;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPlaceFilter implements Filter {
    private String name;

    @NonNull
    private Double longitude;

    @NotNull
    private Double latitude;

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
