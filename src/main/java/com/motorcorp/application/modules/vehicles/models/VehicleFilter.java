package com.motorcorp.application.modules.vehicles.models;

import com.motorcorp.models.BaseFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VehicleFilter extends BaseFilter {
    private Long customerId;
}
