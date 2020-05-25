package com.pmu.data.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {

    Optional<UserDetail> findByEmailAndDeletedFalse(String email);
}
