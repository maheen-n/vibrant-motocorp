package com.motorcorp.application.modules.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketView extends AbstractView {

    private String name;

    private String phone;

    private String email;

    private String comment;

    private Type.Ticket ticket;

    private String ticketFor;

    @JsonIgnoreProperties({"createdOn", "updatedOn", "status"})
    private CustomerView customer;

    private Status.Ticket ticketStatus;

    public void setCustomer(Customer customer) {
        if (customer == null) return;
        this.customer = customer.getView(CustomerView.class);
    }
}
