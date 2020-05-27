package com.pmu.service.users;

import com.pmu.data.model.places.LatLng;
import com.pmu.data.model.places.Place;
import com.pmu.data.model.places.UserDetailPlaceAssignment;
import com.pmu.data.model.users.UserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserDetail createUser(UserDetail userDetail);

    UserDetail findById(UUID id);

    void checkUserOnPlace(Place place, LatLng currentPlace);

    List<UserDetailPlaceAssignment> findAllCheckedPlaces();
}
