package com.motorcorp.application.modules.maintenance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MaintenanceUpdateRequest extends AbstractRequestUpdateEntity<com.motorcorp.application.modules.maintenance.dto.Maintenance> {

    private Type.Service serviceType;

    private Integer ODOReading;

    private Status.Maintenance maintenanceStatus;

    private CustomerRequest customerInfo;

    private VehicleRequest vehicleInfo;

    private Integer totalAmountFixed;

    private Integer additionalCost;

    @JsonIgnore
    private Long initiatedId;

    @Override
    public Maintenance update(Maintenance entity) {
        if (serviceType != null) entity.setServiceType(serviceType);
        if (ODOReading != null) entity.setODOReading(ODOReading);
        if (maintenanceStatus != null) entity.setMaintenanceStatus(maintenanceStatus);
        if (totalAmountFixed != null) entity.setTotalAmountFixed(totalAmountFixed);
        if (additionalCost != null) entity.setAdditionalCost(additionalCost);
        return entity;
    }
}
