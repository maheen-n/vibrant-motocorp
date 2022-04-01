package com.motorcorp.application.modules.vehicles.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.models.models.VehicleModelView;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
public class VehicleView extends AbstractView {
    private String name;

    private String registrationNumber;

    private String chassisNumber;

    private String engineNumber;

    @JsonIgnoreProperties({"createdOn", "updatedOn", "status"})
    private VehicleModelView vehicleModel;

    private String manufactureYear;

    private LocalDate dateOfSale;

    private LocalDate lastService;

    private Integer lastServiceODO;

    private LocalDate insurance;

    private LocalDate nextServiceAt;

    private Integer nextServiceOn;

    @JsonIgnoreProperties({"createdOn", "updatedOn", "status"})
    private CustomerView customer;

    public void setCustomer(Customer customer) {
        this.customer = customer.getView(CustomerView.class);
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel.getView(VehicleModelView.class);
    }
}
