package com.motorcorp.application.modules.maintenance.dto;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.motorcorp.application.constants.DbConstants.MAINTENANCE;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = MAINTENANCE)
public class Maintenance extends AbstractTransactionalEntity {
    @Enumerated(EnumType.STRING)
    private Type.Service serviceType;

    private Integer ODOReading;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CustomerInfo customerInfo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private VehicleInfo vehicleInfo;

    @Enumerated(EnumType.STRING)
    private Status.Maintenance maintenanceStatus;

    private Integer totalAmountFixed;

    private Integer additionalCost;

    private Float totalExpense;
}
