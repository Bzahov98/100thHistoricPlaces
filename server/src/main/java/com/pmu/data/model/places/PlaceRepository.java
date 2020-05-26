package com.pmu.data.model.places;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

interface PlaceRepository extends JpaRepository<Place, UUID>, QuerydslPredicateExecutor<Place> {
}
