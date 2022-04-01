package com.motorcorp.application.modules.vehicles.services;

import com.motorcorp.application.modules.vehicles.models.VehicleCreateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleFilter;
import com.motorcorp.application.modules.vehicles.models.VehicleUpdateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface VehicleService {
    VehicleView create(VehicleCreateRequest request);

    VehicleView get(Long id);

    PaginatedList<VehicleView> get(VehicleFilter filter);

    VehicleView update(Long id, VehicleUpdateRequest updateRequest);

    VehicleView delete(Long id, Long userId);
}
