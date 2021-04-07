package com.example.demoCarsForSale.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class JwtResponse {
    private String token;
}
