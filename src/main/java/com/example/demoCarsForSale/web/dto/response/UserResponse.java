package com.example.demoCarsForSale.web.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponse {
    @JsonIgnore
    private long userId;
    private String userName;
}
