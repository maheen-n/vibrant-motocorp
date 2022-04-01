package com.motorcorp.application.modules.vehicles.dto;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Set;

import static com.motorcorp.application.constants.DbConstants.VEHICLE;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = VEHICLE)
public class Vehicle extends AbstractTransactionalEntity {

    private String name;

    private String registrationNumber;

    private String chassisNumber;

    private String engineNumber;

    @Column(name = "model_id")
    private Long modelId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private VehicleModel vehicleModel;

    private String manufactureYear;

    private LocalDate dateOfSale;

    private LocalDate lastService;

    private Integer lastServiceODO;

    private LocalDate insurance;

    private LocalDate nextServiceAt;

    private Integer nextServiceOn;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
        this.modelId = vehicleModel.getId();
    }
}
