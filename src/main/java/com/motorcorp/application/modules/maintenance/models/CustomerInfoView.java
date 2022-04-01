package com.motorcorp.application.modules.maintenance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfoView extends AbstractView {
    private Long customerId;

    private String name;

    private String phone;
}
