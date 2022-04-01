package com.motorcorp.application.modules.customer.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerView extends AbstractView {
    private String name;

    private String phone;

    private String alternativePhone;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String pinCode;
}
