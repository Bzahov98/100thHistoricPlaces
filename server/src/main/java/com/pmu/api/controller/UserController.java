package com.pmu.api.controller;

import com.pmu.api.dto.request.ApiUserRegistrationRequest;
import com.pmu.api.dto.response.ApiUserDetailResponse;
import com.pmu.data.model.users.UserDetail;
import com.pmu.mapping.ModelMapper;
import com.pmu.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    @PostMapping("/users/signup")
    public ApiUserDetailResponse registerUser(@RequestBody ApiUserRegistrationRequest apiUserRegistrationRequest) {
        return modelMapper.map(userService.createUser(modelMapper.map(apiUserRegistrationRequest, UserDetail.class)), ApiUserDetailResponse.class);
    }

}
