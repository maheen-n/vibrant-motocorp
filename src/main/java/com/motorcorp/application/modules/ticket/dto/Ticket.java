package com.motorcorp.application.modules.ticket.dto;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.motorcorp.application.constants.DbConstants.TICKET;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TICKET)
public class Ticket extends AbstractTransactionalEntity {
    private String name;

    private String phone;

    private String email;

    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type.Ticket ticket;

    private String ticketFor;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status.Ticket ticketStatus;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
