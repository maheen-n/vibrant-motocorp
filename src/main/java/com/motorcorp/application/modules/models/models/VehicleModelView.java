package com.motorcorp.application.modules.models.models;

import com.motorcorp.application.enums.Role;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleModelView extends AbstractView {
    private String name;

    private String variant;

    private String description;
}
