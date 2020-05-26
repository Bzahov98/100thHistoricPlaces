package com.pmu.service.users;

import com.pmu.data.model.users.UserDetail;
import com.pmu.data.model.users.UserDetailDataService;
import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDetailDataService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new User(userService.findByEmail(email).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public UserDetail createUser(UserDetail userDetail) {
        userService.findByEmail(userDetail.getEmail()).ifPresent(userDetail1 -> {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_USED);
        });

        if (StringUtils.isEmpty(userDetail.getPassword()) || userDetail.getPassword().length() < 8)
            throw new BaseException(ErrorCode.INCORRECT_FORMAT_OF_PASSWORD);

        userDetail.setPassword(passwordEncoder.encode(userDetail.getPassword()));
        return userService.saveUser(userDetail);
    }
}
