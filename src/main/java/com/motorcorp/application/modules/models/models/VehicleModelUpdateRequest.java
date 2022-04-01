package com.motorcorp.application.modules.models.models;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_EMAIL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class VehicleModelUpdateRequest extends AbstractRequestUpdateEntity<VehicleModel> {

    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    private String variant;

    private String description;

    @Override
    public VehicleModel update(VehicleModel entity) {
        if (name != null) entity.setName(name);
        if (variant != null) entity.setVariant(variant);
        if (description != null) entity.setDescription(description);
        return entity;
    }
}
