package com.pmu.api.controller;

import com.pmu.api.dto.ApiUserRegistrationRequest;
import com.pmu.data.model.user.UserDetail;
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

    @PostMapping("/register")
    public UserDetail registerUser(@RequestBody ApiUserRegistrationRequest apiUserRegistrationRequest) {
        return userService.createUser(modelMapper.map(apiUserRegistrationRequest, UserDetail.class));
    }

}
