package com.motorcorp.application.modules.customer.services;

import com.motorcorp.application.modules.customer.models.CustomerCreateRequest;
import com.motorcorp.application.modules.customer.models.CustomerFilter;
import com.motorcorp.application.modules.customer.models.CustomerUpdateRequest;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface CustomerService {
    CustomerView create(CustomerCreateRequest request);

    CustomerView get(Long id);

    PaginatedList<CustomerView> get(CustomerFilter filter);

    CustomerView update(Long id, CustomerUpdateRequest updateRequest);

    CustomerView delete(Long id, Long userId);
}
