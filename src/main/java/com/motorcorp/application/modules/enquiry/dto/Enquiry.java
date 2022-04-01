package com.motorcorp.application.modules.enquiry.dto;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
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

import static com.motorcorp.application.constants.DbConstants.ENQUIRY;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ENQUIRY)
public class Enquiry extends AbstractTransactionalEntity {
    private String name;

    private String phone;

    private String email;

    private String comment;

    private Type.Enquiry enquiry;

    private String enquiryFor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status.Enquiry enquiryStatus;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
