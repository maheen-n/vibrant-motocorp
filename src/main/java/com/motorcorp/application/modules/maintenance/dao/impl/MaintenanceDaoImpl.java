package com.motorcorp.application.modules.maintenance.dao.impl;

import com.motorcorp.application.modules.maintenance.dao.MaintenanceDao;
import com.motorcorp.application.modules.maintenance.dao.MaintenanceSpecifications;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.maintenance.models.MaintenanceFilter;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.models.BaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceDaoImpl extends MaintenanceSpecifications<Maintenance> implements MaintenanceDao {
    @Autowired
    private MaintenanceRepository maintenanceRepo;

    @Override
    public void save(Maintenance maintenance) {
        maintenanceRepo.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Maintenance> findAll(MaintenanceFilter filter) {
        Specification<Maintenance> specification = Specification.where(
                query(filter.getQuery())
                        .and(vehicleId(filter.getVehicleId()))
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(maintenanceStatus(filter.getMaintenanceStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
        );

        return maintenanceRepo.findAll(specification, filter.filterPageable());
    }
}
