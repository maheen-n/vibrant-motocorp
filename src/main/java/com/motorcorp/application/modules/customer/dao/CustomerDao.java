package com.motorcorp.application.modules.customer.dao;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerFilter;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

public interface CustomerDao {
    void save(Customer customer);

    Customer findById(Long id);

    Page<Customer> findAll(CustomerFilter filter);
}
