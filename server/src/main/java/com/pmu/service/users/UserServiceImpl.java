package com.pmu.service.users;

import com.pmu.api.context.ContextHolder;
import com.pmu.data.model.places.LatLng;
import com.pmu.data.model.places.Place;
import com.pmu.data.model.places.UserDetailPlaceAssignment;
import com.pmu.data.model.users.UserDetail;
import com.pmu.data.model.users.UserDetailDataService;
import com.pmu.exception.BaseException;
import com.pmu.exception.ErrorCode;
import com.pmu.util.DistanceUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    @Override
    public UserDetail findById(UUID id) {
        return userService.findById(id);
    }

    @Override
    public void checkUserOnPlace(Place place, LatLng currentPlace) {
        if (DistanceUtil.calculateDistance(place.getLatLng(), currentPlace) > 1)
            throw new BaseException(ErrorCode.NOT_ENOUGH_CLOSE_TO_PLACE);
        UserDetail userDetail = userService.findById(ContextHolder.get().getUserId());
        userDetail.getCheckedPlaces().add(UserDetailPlaceAssignment.builder()
                .place(place)
                .userDetail(userDetail)
                .build());
        userDetail.setPoints(userDetail.getPoints().add(BigDecimal.valueOf(place.getPoints())));

        userService.saveUser(userDetail);
    }

    @Override
    public List<UserDetailPlaceAssignment> findAllCheckedPlaces() {
        return userService.findById(ContextHolder.get().getUserId()).getCheckedPlaces();
    }
}
