package com.pmu.data.model.users;

import java.util.Optional;
import java.util.UUID;

public interface UserDetailDataService {
    UserDetail findById(UUID id);

    Optional<UserDetail> findByEmail(String email);

    UserDetail saveUser(UserDetail userDetail);
}
