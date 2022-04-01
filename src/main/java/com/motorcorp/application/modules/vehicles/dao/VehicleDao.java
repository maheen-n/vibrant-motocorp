package com.motorcorp.application.modules.vehicles.dao;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.application.modules.vehicles.models.VehicleFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

public interface VehicleDao {
    void save(Vehicle customer);

    Vehicle findById(Long id);

    Page<Vehicle> findAll(VehicleFilter filter);
}
