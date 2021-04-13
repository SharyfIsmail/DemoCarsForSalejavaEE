package com.example.demoCarsForSale.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PhoneRequest {
    @NotEmpty(message = "Phone may not be empty")
    @Size(min = 12, max = 13, message = "Phone number must be 12 or 13 characters long")
    private String phone;
}
