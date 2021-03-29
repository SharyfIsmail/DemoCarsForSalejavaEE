package com.example.demoCarsForSale.mapper;

import com.example.demoCarsForSale.controllers.dto.request.PhoneRequest;
import com.example.demoCarsForSale.controllers.dto.response.PhoneResponse;
import com.example.demoCarsForSale.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneRequestResponseMapper {

    public static List<UserPhone> convertPhoneRequestToUserPhone(List<PhoneRequest> phones) {
        return phones.stream()
            .filter(x -> x.getPhone() != null)
            .map(x -> UserPhone.builder()
                .phone(x.getPhone())
                .build())
            .collect(Collectors.toList());
    }

    public static List<PhoneResponse> convertUserPhoneToPhoneResponse(List<UserPhone> phones) {
        return phones.stream()
            .map(x -> PhoneResponse.builder()
                .phone(x.getPhone())
                .build()
            )
            .collect(Collectors.toList());
    }
}
