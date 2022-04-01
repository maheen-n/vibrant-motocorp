package com.motorcorp.application.modules.maintenance.dao;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.maintenance.models.MaintenanceFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

public interface MaintenanceDao {
    void save(Maintenance maintenance);

    Maintenance findById(Long id);

    Page<Maintenance> findAll(MaintenanceFilter filter);
}
