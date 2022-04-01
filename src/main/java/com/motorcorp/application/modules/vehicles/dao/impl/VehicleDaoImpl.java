package com.motorcorp.application.modules.vehicles.dao.impl;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.vehicles.dao.VehicleDao;
import com.motorcorp.application.modules.vehicles.dao.VehicleSpecifications;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.application.modules.vehicles.models.VehicleFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class VehicleDaoImpl extends VehicleSpecifications<Vehicle> implements VehicleDao {
    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Vehicle> findAll(VehicleFilter filter) {
        Specification<Vehicle> specification = Specification.where(
                query(filter.getQuery())
                        .and(customerId(filter.getCustomerId()))
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
        );

        return vehicleRepo.findAll(specification, filter.filterPageable());
    }
}
