package com.motorcorp.application.modules.customer.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_EMAIL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class CustomerCreateRequest extends AbstractRequestEntity<Customer> {
    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    @Pattern(regexp = REGEX_PHONE)
    private String alternativePhone;

    @Pattern(regexp = REGEX_EMAIL)
    private String email;

    private String addressLine1;

    private String addressLine2;

    private String city;

    @JsonAlias({"postalCode", "pincode"})
    private String pinCode;

    @JsonIgnore
    private Long initiatedId;

}
