package com.motorcorp.application.modules.vehicles.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;

@Getter
@Setter
public class VehicleCreateRequest extends AbstractRequestEntity<Vehicle> {
    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    private String registrationNumber;

    private String chassisNumber;

    private String engineNumber;

    @NotNull
    private Long modelId;

    private String manufactureYear;

    private LocalDate dateOfSale;

    private LocalDate lastService;

    private Integer lastServiceODO;

    private LocalDate insurance;

    @NotNull
    private Long customerId;

    @JsonIgnore
    private Long initiatedId;
}
