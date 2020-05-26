package com.pmu.service.users;

import com.pmu.data.model.users.UserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDetail createUser(UserDetail userDetail);
}
