package com.motorcorp.application.modules.models.services;

import com.motorcorp.application.modules.models.models.VehicleModelCreateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelFilter;
import com.motorcorp.application.modules.models.models.VehicleModelUpdateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface VehicleModelService {
    VehicleModelView create(VehicleModelCreateRequest request);

    VehicleModelView get(Long id);

    PaginatedList<VehicleModelView> get(VehicleModelFilter filter);

    VehicleModelView update(Long id, VehicleModelUpdateRequest updateRequest);

    VehicleModelView delete(Long id);
}
