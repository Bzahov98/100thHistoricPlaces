package com.pmu.data.model.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserPasswordHistory {

    @Id
    private UUID id = UUID.randomUUID();

    private String oldPassword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userDetail;

    private OffsetDateTime createdDateTime = OffsetDateTime.now();
}