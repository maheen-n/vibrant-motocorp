package com.motorcorp.application.modules.customer.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
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
public class CustomerUpdateRequest extends AbstractRequestUpdateEntity<Customer> {
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

    @Override
    public Customer update(Customer entity) {
        if (name != null) entity.setName(name);
        if (phone != null) entity.setPhone(phone);
        if (alternativePhone != null) entity.setAlternativePhone(phone);
        if (email != null) entity.setEmail(email);
        if (addressLine1 != null) entity.setAddressLine1(addressLine1);
        if (addressLine2 != null) entity.setAddressLine2(addressLine2);
        if (city != null) entity.setCity(city);
        if (pinCode != null) entity.setPinCode(pinCode);
        return entity;
    }
}
