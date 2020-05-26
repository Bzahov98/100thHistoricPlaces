package com.pmu.api.dto.response;

import com.neovisionaries.i18n.CountryCode;
import com.pmu.data.model.users.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ApiUserDetailResponse {

    private UUID id = UUID.randomUUID();

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private CountryCode nationality;

    private UserStatus status;
}
