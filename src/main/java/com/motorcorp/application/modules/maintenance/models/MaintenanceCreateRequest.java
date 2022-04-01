package com.motorcorp.application.modules.maintenance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.maintenance.dto.CustomerInfo;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.maintenance.dto.VehicleInfo;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MaintenanceCreateRequest extends AbstractRequestEntity<Maintenance> {
    @NotNull
    private Type.Service serviceType;

    @NotNull
    private Integer ODOReading;

    @NotNull
    private CustomerRequest customerInfo;

    @NotNull
    private VehicleRequest vehicleInfo;

    @Max(1000000)
    private Integer totalAmountFixed;

    private Integer additionalCost;

    @JsonIgnore
    private Long initiatedId;

}
