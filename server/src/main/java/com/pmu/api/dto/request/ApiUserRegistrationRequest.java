package com.pmu.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ApiUserRegistrationRequest {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CountryCode nationality;

}
