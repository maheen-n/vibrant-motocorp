package com.motorcorp.application.modules.models.dto;

import com.motorcorp.application.enums.Role;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static com.motorcorp.application.constants.DbConstants.USER;
import static com.motorcorp.application.constants.DbConstants.VEHICLE_MODEL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = VEHICLE_MODEL)
public class VehicleModel extends AbstractTransactionalEntity {
    private String name;

    private String variant;

    private String description;
}
