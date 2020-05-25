package com.pmu.data.model.user;

import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDetailDataServiceImpl implements UserDetailDataService {

    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetail findById(UUID id) {
        return userDetailRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDetail findByEmail(String email) {
        return userDetailRepository.findByEmailAndDeletedFalse(email).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDetail saveUser(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }
}
