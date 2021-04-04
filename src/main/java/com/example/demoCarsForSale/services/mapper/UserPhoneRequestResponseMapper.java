package com.example.demoCarsForSale.services.mapper;

import com.example.demoCarsForSale.web.dto.request.PhoneRequest;
import com.example.demoCarsForSale.web.dto.response.PhoneResponse;
import com.example.demoCarsForSale.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneRequestResponseMapper {

    public static List<UserPhone> toUserPhones(List<PhoneRequest> phones) {
        if (phones == null) {
            return Collections.emptyList();
        }

        return phones.stream()
            .filter(x -> x.getPhone() != null)
            .map(x -> UserPhone.builder()
                .phone(x.getPhone())
                .build())
            .collect(Collectors.toList());
    }

    public static List<PhoneResponse> toResponses(List<UserPhone> phones) {
        return phones.stream()
            .map(x -> PhoneResponse.builder()
                .phone(x.getPhone())
                .build()
            )
            .collect(Collectors.toList());
    }
}
