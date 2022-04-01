package com.motorcorp.application.modules.customer.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.motorcorp.application.constants.DbConstants.CUSTOMER;
import static com.motorcorp.util.RegexConstants.REGEX_EMAIL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CUSTOMER)
public class Customer extends AbstractTransactionalEntity {
    private String name;

    private String phone;

    private String alternativePhone;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String city;

    @JsonAlias({"postalCode", "pincode"})
    private String pinCode;
}
