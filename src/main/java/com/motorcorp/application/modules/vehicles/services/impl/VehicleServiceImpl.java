package com.motorcorp.application.modules.vehicles.services.impl;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.models.dao.VehicleModelDao;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.vehicles.dao.VehicleDao;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.application.modules.vehicles.models.VehicleCreateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleFilter;
import com.motorcorp.application.modules.vehicles.models.VehicleUpdateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
import com.motorcorp.application.modules.vehicles.services.VehicleService;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private VehicleModelDao vehicleModelDao;

    @Override
    @Transactional
    public VehicleView create(VehicleCreateRequest request) {
        VehicleModel vehicleModel = vehicleModelDao.findById(request.getModelId());
        if (vehicleModel == null) {
            log.error("Vehicle creation failed :: unknown vehicle model id {}", request.getModelId());
            throw new ServiceException(ErrorCodeEnum.MODEL_NOT_FOUND);
        }
        Customer customer = customerDao.findById(request.getCustomerId());
        if (customer == null) {
            log.error("Vehicle creation failed :: unknown customer id {}", request.getCustomerId());
            throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
        }

        Vehicle vehicle = request.getEntity();
        vehicle.setVehicleModel(vehicleModel);
        vehicle.setCustomer(customer);
        vehicle.setStatus(EntityStatus.ACTIVE);
        vehicleDao.save(vehicle);
        activityService.insert(Activity.builder()
                .category(Type.Category.VEHICLE)
                .activity(Type.Activity.CREATED)
                .activityFor(vehicle.getId())
                .userId(request.getInitiatedId())
                .build());
        activityService.insert(Activity.builder()
                .category(Type.Category.CUSTOMER)
                .activity(Type.Activity.VEHICLE_ADDED)
                .activityFor(customer.getId())
                .activityId(vehicle.getId())
                .build());
        return vehicle.getView(VehicleView.class);
    }

    @Override
    public VehicleView get(Long id) {
        Vehicle vehicle = vehicleDao.findById(id);
        if (vehicle == null) {
            log.error("Vehicle not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.VEHICLE_NOT_FOUND);
        }
        return vehicle.getView(VehicleView.class);
    }

    @Override
    public PaginatedList<VehicleView> get(VehicleFilter filter) {
        Page<Vehicle> page = vehicleDao.findAll(filter);
        return new PaginatedList<>(page, VehicleView.class);
    }

    @Override
    public VehicleView update(Long id, VehicleUpdateRequest updateRequest) {
        Vehicle vehicle = vehicleDao.findById(id);
        if (vehicle == null) {
            log.error("Vehicle not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.VEHICLE_NOT_FOUND);
        }
        VehicleModel vehicleModel = vehicleModelDao.findById(updateRequest.getModelId());
        if (vehicleModel == null) {
            log.error("Vehicle creation failed :: unknown vehicle model id {}", updateRequest.getModelId());
            throw new ServiceException(ErrorCodeEnum.MODEL_NOT_FOUND);
        }
        vehicle.setVehicleModel(vehicleModel);
        Customer customer = customerDao.findById(updateRequest.getCustomerId());
        if (customer == null) {
            log.error("vehicle update failed :: unknown customer id {}", updateRequest.getCustomerId());
            throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
        }
        int hash = vehicle.hashCode();
        vehicle.setCustomer(customer);
        updateRequest.update(vehicle);
        if (hash == vehicle.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        vehicleDao.save(vehicle);
        return vehicle.getView(VehicleView.class);
    }

    @Override
    public VehicleView delete(Long id, Long userId) {
        Vehicle vehicle = vehicleDao.findById(id);
        if (vehicle == null) {
            log.error("Vehicle not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.VEHICLE_NOT_FOUND);
        }
        vehicle.setStatus(EntityStatus.DELETED);
        vehicleDao.save(vehicle);
        activityService.insert(Activity.builder()
                .category(Type.Category.VEHICLE)
                .activity(Type.Activity.DELETED)
                .activityFor(id)
                .userId(userId)
                .build());
        return vehicle.getView(VehicleView.class);
    }
}
