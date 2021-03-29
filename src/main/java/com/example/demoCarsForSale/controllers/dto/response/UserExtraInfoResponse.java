package com.example.demoCarsForSale.controllers.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserExtraInfoResponse {
    private String userName;
    private String email;
    private long adCount;
}
