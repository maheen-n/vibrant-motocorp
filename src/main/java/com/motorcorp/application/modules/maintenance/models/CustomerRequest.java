package com.motorcorp.application.modules.maintenance.models;

import com.motorcorp.application.modules.maintenance.dto.CustomerInfo;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class CustomerRequest extends AbstractRequestEntity<CustomerInfo> {
    private Long customerId;

    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

}
