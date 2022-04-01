package com.motorcorp.application.modules.enquiry.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.maintenance.models.CustomerInfoView;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class EnquiryView extends AbstractView {

    private String name;

    private String phone;

    private String email;

    private Type.Enquiry enquiry;

    private String enquiryFor;

    @JsonIgnoreProperties({"createdOn", "updatedOn", "status"})
    private CustomerView customer;

    private Status.Enquiry enquiryStatus;

    public void setCustomer(Customer customer) {
        if (customer == null) return;
        this.customer = customer.getView(CustomerView.class);
    }
}
