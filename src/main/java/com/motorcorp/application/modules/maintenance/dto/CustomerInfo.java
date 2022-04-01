package com.motorcorp.application.modules.maintenance.dto;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.dto.AbstractTransactionalEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.motorcorp.application.constants.DbConstants.CUSTOMER_INFO;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CUSTOMER_INFO)
public class CustomerInfo extends AbstractTransactionalEntity {
    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    private String name;

    private String phone;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }
}
