package com.pmu.service.users;

import com.pmu.data.model.user.UserDetail;
import com.pmu.data.model.user.UserDetailDataService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDetailDataService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new User(userService.findByEmail(email));
    }

    @Override
    public UserDetail createUser(UserDetail userDetail) {
        userDetail.setPassword(passwordEncoder.encode(userDetail.getPassword()));
        return userService.saveUser(userDetail);
    }
}
