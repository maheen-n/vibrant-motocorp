package com.motorcorp.application.modules.maintenance.service;

import com.motorcorp.application.modules.maintenance.models.CustomerInfoView;
import com.motorcorp.application.modules.maintenance.models.CustomerRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceCreateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceFilter;
import com.motorcorp.application.modules.maintenance.models.MaintenanceUpdateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceView;
import com.motorcorp.application.modules.maintenance.models.VehicleInfoView;
import com.motorcorp.application.modules.maintenance.models.VehicleRequest;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface MaintenanceService {
    MaintenanceView create(MaintenanceCreateRequest request);

    MaintenanceView get(Long id);

    PaginatedList<MaintenanceView> get(MaintenanceFilter filter);

    MaintenanceView update(Long id, MaintenanceUpdateRequest updateRequest);

    MaintenanceView delete(Long id, Long userId);

    VehicleInfoView updateMaintenanceVehicle(Long maintenanceId, VehicleRequest vehicleRequest);

    CustomerInfoView updateMaintenanceCustomer(Long maintenanceId, CustomerRequest customerRequest);
}
