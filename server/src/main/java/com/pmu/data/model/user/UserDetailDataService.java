package com.pmu.data.model.user;

import java.util.UUID;

public interface UserDetailDataService {
    UserDetail findById(UUID id);

    UserDetail findByEmail(String email);

    UserDetail saveUser(UserDetail userDetail);
}
