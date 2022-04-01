package com.motorcorp.application.modules.models.dao.impl;

import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.models.dao.VehicleModelDao;
import com.motorcorp.application.modules.models.dao.VehicleModelsSpecifications;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.models.models.VehicleModelFilter;
import com.motorcorp.models.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class VehicleModelDaoImpl extends VehicleModelsSpecifications<VehicleModel> implements VehicleModelDao {
    @Autowired
    private VehicleModelRepository userRepo;

    @Override
    public void save(VehicleModel user) {
        userRepo.save(user);
    }

    @Override
    public VehicleModel findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public Page<VehicleModel> findAll(VehicleModelFilter filter) {
        Specification<VehicleModel> specification = Specification.where(
                query(filter.getQuery())
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
        );
        return userRepo.findAll(specification, filter.filterPageable());
    }

}
