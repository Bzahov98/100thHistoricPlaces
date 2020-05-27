package com.pmu.data.model.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

interface UserDetailRepository extends JpaRepository<UserDetail, UUID>, QuerydslPredicateExecutor {

    Optional<UserDetail> findByEmailAndDeletedFalse(String email);
}
