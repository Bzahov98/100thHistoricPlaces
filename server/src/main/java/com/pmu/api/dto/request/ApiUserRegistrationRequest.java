package com.pmu.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ApiUserRegistrationRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private CountryCode nationality;

}
