package com.pmu.api.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ApiPlaceCheckResponse {
    private UUID placeId;
    private OffsetDateTime checkDate;
}
