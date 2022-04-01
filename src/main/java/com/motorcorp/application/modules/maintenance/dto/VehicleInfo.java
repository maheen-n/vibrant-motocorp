package com.motorcorp.application.modules.maintenance.dto;

import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDate;

import static com.motorcorp.application.constants.DbConstants.VEHICLE_INFO;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = VEHICLE_INFO)
public class VehicleInfo extends AbstractTransactionalEntity {
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", insertable = false, updatable = false)
    private Vehicle vehicle;

    private String model;

    private String registrationNumber;

    private LocalDate nextServiceAt;

    private Integer nextServiceOn;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.vehicleId = vehicle.getId();
    }
}
