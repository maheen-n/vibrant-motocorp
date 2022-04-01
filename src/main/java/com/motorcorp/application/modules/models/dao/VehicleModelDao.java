package com.motorcorp.application.modules.models.dao;

import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.models.models.VehicleModelFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VehicleModelDao {
    void save(VehicleModel user);

    VehicleModel findById(Long id);

    Page<VehicleModel> findAll(VehicleModelFilter filter);

}
