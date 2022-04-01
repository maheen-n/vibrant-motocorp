package com.motorcorp.application.modules.maintenance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
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
public class VehicleInfoView extends AbstractView {
    private Long vehicleId;

    private String registrationNumber;

    private String model;

    private LocalDate nextServiceAt;

    private Integer nextServiceOn;

}
