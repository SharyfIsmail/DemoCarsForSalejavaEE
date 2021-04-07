package com.example.demoCarsForSale.web.dto.request;

import com.example.demoCarsForSale.dao.model.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdRequest {
    @NotNull(message = "Year is required")
    private Integer year;
    @NotBlank(message = "need to define the car's brand")
    private String brand;
    @NotBlank(message = "need to define the car's model")
    private String model;
    @NotNull(message = "Engine capacity is required")
    private Integer engineCapacity;
    @NotNull(message = "Mileage is required")
    private Integer mileAge;
    @NotBlank(message = "Condition is required")
    private Condition condition;
    @NotNull(message = "Mileage is required")
    private Integer power;
    @NotNull(message = "please upload at least one picture")
    private List<PicRequest> pics;
    @NotNull(message = "please type at least one phone number")
    private List<PhoneRequest> phones;
}
