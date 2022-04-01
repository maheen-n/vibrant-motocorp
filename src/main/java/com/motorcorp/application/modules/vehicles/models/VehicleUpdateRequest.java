package com.motorcorp.application.modules.vehicles.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
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
public class VehicleUpdateRequest extends AbstractRequestUpdateEntity<Vehicle> {

    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    private String registrationNumber;

    private String chassisNumber;

    private String engineNumber;

    private Long modelId;

    private String manufactureYear;

    private LocalDate dateOfSale;

    private LocalDate lastService;

    private Integer lastServiceODO;

    private LocalDate insurance;

    private Long customerId;

    @JsonIgnore
    private String initiatedId;

    @Override
    public Vehicle update(Vehicle entity) {
        if (name != null) entity.setName(name);
        if (registrationNumber != null) entity.setRegistrationNumber(registrationNumber);
        if (chassisNumber != null) entity.setChassisNumber(chassisNumber);
        if (engineNumber != null) entity.setEngineNumber(engineNumber);
        if (manufactureYear != null) entity.setManufactureYear(manufactureYear);
        if (dateOfSale != null) entity.setDateOfSale(dateOfSale);
        if (lastService != null) entity.setLastService(lastService);
        if (lastServiceODO != null) entity.setLastServiceODO(lastServiceODO);
        if (insurance != null) entity.setInsurance(insurance);
        return entity;
    }
}
