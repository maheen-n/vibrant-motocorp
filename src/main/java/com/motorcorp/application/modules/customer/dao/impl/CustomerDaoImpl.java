package com.motorcorp.application.modules.customer.dao.impl;

import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dao.CustomerSpecifications;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CustomerDaoImpl extends CustomerSpecifications<Customer> implements CustomerDao {
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Customer> findAll(CustomerFilter filter) {
        Specification<Customer> specification = Specification.where(
                query(filter.getQuery())
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
        );
        return customerRepo.findAll(specification, filter.filterPageable());
    }
}
