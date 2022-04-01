package com.motorcorp.application.modules.maintenance.models;

import com.motorcorp.application.modules.maintenance.dto.VehicleInfo;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.dto.AbstractRequestEntity;
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
public class VehicleRequest extends AbstractRequestEntity<VehicleInfo> {

    private Long vehicleId;

    private String model;

    private String registrationNumber;

    private LocalDate nextServiceAt;

    private Integer nextServiceOn;
}
