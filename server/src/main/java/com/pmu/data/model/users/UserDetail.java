package com.pmu.data.model.users;

import com.google.common.collect.Lists;
import com.neovisionaries.i18n.CountryCode;
import com.pmu.data.model.places.Place;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserDetail {
    @Id
    private UUID id = UUID.randomUUID();

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private CountryCode nationality;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "userDetail", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<UserPasswordHistory> passwordHistoryList = Lists.newArrayList();


    @ManyToMany
    @JoinTable(
            name = "user_detail_place",
            joinColumns = @JoinColumn(name = "user_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id"))
    private Set<Place> checkedPlaces = Set.of();

    private Boolean deleted = false;
}
