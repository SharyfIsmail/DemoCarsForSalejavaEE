package com.example.demoCarsForSale.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class UserDto {
    private long userId;
    private String userName;
    private String userPassword;
}
