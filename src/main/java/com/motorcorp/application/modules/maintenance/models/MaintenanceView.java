package com.motorcorp.application.modules.maintenance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.maintenance.dto.CustomerInfo;
import com.motorcorp.application.modules.maintenance.dto.VehicleInfo;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Getter
@Setter
public class MaintenanceView extends AbstractView {
    private Type.Service serviceType;

    private Integer ODOReading;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id","createdOn", "updatedOn", "status"})
    private CustomerInfoView customerInfo;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id","createdOn", "updatedOn", "status"})
    private VehicleInfoView vehicleInfo;

    private Status.Maintenance maintenanceStatus;

    private Integer totalAmountFixed;

    private Integer additionalCost;

    private Float totalExpense;

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo.getView(CustomerInfoView.class);
    }

    public void setVehicleInfo(VehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo.getView(VehicleInfoView.class);
    }
}
