package com.motorcorp.application.modules.maintenance.service.impl;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.maintenance.dao.MaintenanceDao;
import com.motorcorp.application.modules.maintenance.dto.CustomerInfo;
import com.motorcorp.application.modules.maintenance.dto.Maintenance;
import com.motorcorp.application.modules.maintenance.dto.VehicleInfo;
import com.motorcorp.application.modules.maintenance.models.CustomerInfoView;
import com.motorcorp.application.modules.maintenance.models.CustomerRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceCreateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceFilter;
import com.motorcorp.application.modules.maintenance.models.MaintenanceUpdateRequest;
import com.motorcorp.application.modules.maintenance.models.MaintenanceView;
import com.motorcorp.application.modules.maintenance.models.VehicleInfoView;
import com.motorcorp.application.modules.maintenance.models.VehicleRequest;
import com.motorcorp.application.modules.maintenance.service.MaintenanceService;
import com.motorcorp.application.modules.vehicles.dao.VehicleDao;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceDao maintenanceDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public MaintenanceView create(MaintenanceCreateRequest request) {
        Maintenance maintenance = request.getEntity();
        CustomerInfo customerInfo = request.getCustomerInfo().getEntity();
        if (customerInfo.getCustomerId() != null) {
            Customer customer = customerDao.findById(customerInfo.getCustomerId());
            if (customer == null) {
                log.error("Customer {} not found", customerInfo.getCustomerId());
                throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
            }
            customerInfo.setCustomer(customer);
        }
        maintenance.setCustomerInfo(customerInfo);
        VehicleInfo vehicleInfo = request.getVehicleInfo().getEntity();
        if (vehicleInfo.getVehicleId() != null) {
            Vehicle vehicle = vehicleDao.findById(vehicleInfo.getVehicleId());
            if (vehicle == null) {
                log.error("Vehicle {} not found", vehicleInfo.getVehicleId());
                throw new ServiceException(ErrorCodeEnum.VEHICLE_NOT_FOUND);
            }
        /*    if (customerInfo.getCustomer() != null && !vehicle.getCustomerId().equals(customerInfo.getCustomerId())) {
                log.error("Vehicle owner does not match with maintenance vehicle customer info", vehicleInfo.getVehicleId());
                throw new ServiceException(ErrorCodeEnum.VEHICLE_OWNER_NOT_MATCHING);
            }*/
            vehicleInfo.setVehicle(vehicle);
            vehicle.setLastService(LocalDate.now());
            vehicle.setLastServiceODO(maintenance.getODOReading() == null ? vehicle.getLastServiceODO() : maintenance.getODOReading());
            vehicleDao.save(vehicle);
        }
        maintenance.setVehicleInfo(vehicleInfo);
        maintenance.setStatus(EntityStatus.ACTIVE);
        maintenance.setMaintenanceStatus(Status.Maintenance.NEW);
        maintenanceDao.save(maintenance);

        activityService.insert(Activity.builder()
                .category(Type.Category.MAINTENANCE)
                .activity(Type.Activity.CREATED)
                .activityFor(maintenance.getId())
                .userId(request.getInitiatedId())
                .build());
        if (customerInfo.getCustomer() != null)
            activityService.insert(Activity.builder()
                    .category(Type.Category.CUSTOMER)
                    .activity(Type.Activity.MAINTENANCE_VEHICLE)
                    .activityFor(customerInfo.getId())
                    .activityId(maintenance.getId())
                    .build());
        if (vehicleInfo.getVehicle() != null)
            activityService.insert(Activity.builder()
                    .category(Type.Category.VEHICLE)
                    .activity(Type.Activity.MAINTENANCE_VEHICLE)
                    .activityFor(vehicleInfo.getId())
                    .activityId(maintenance.getId())
                    .build());
        return maintenance.getView(MaintenanceView.class);
    }

    @Override
    public MaintenanceView get(Long id) {
        Maintenance maintenance = maintenanceDao.findById(id);
        if (maintenance == null) {
            log.error("Maintenance {} not found", id);
            throw new ServiceException(ErrorCodeEnum.MAINTENANCE_NOT_FOUND);
        }
        return maintenance.getView(MaintenanceView.class);
    }

    @Override
    public PaginatedList<MaintenanceView> get(MaintenanceFilter filter) {
        Page<Maintenance> page = maintenanceDao.findAll(filter);
        return new PaginatedList<>(page, MaintenanceView.class);
    }

    @Override
    @Transactional
    public MaintenanceView update(Long id, MaintenanceUpdateRequest updateRequest) {
        Maintenance maintenance = maintenanceDao.findById(id);
        if (maintenance == null) {
            log.error("Maintenance {} not found", id);
            throw new ServiceException(ErrorCodeEnum.MAINTENANCE_NOT_FOUND);
        }
        Status.Maintenance oldStatus = maintenance.getMaintenanceStatus();
        int hash = maintenance.hashCode();
        updateRequest.update(maintenance);
        if (hash == maintenance.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        maintenanceDao.save(maintenance);
        if (oldStatus != maintenance.getMaintenanceStatus())
            activityService.insert(Activity.builder()
                    .category(Type.Category.MAINTENANCE)
                    .activity(Type.Activity.STATUS)
                    .from(oldStatus.toString())
                    .to(maintenance.getMaintenanceStatus().toString())
                    .activityFor(id)
                    .userId(updateRequest.getInitiatedId())
                    .build());
        return maintenance.getView(MaintenanceView.class);
    }

    @Override
    @Transactional
    public VehicleInfoView updateMaintenanceVehicle(Long maintenanceId, VehicleRequest vehicleRequest) {
        Maintenance maintenance = maintenanceDao.findById(maintenanceId);
        if (maintenance == null) {
            log.error("Maintenance {} not found", maintenanceId);
            throw new ServiceException(ErrorCodeEnum.MAINTENANCE_NOT_FOUND);
        }
        VehicleInfo vehicleInfo = vehicleRequest.getEntity();
        if (vehicleInfo.getVehicleId() != null) {
            Vehicle vehicle = vehicleDao.findById(vehicleInfo.getVehicleId());
            if (vehicle == null) {
                log.error("Vehicle {} not found", vehicleInfo.getVehicleId());
                throw new ServiceException(ErrorCodeEnum.VEHICLE_NOT_FOUND);
            }
            /*    if (customerInfo.getCustomer() != null && !vehicle.getCustomerId().equals(customerInfo.getCustomerId())) {
                log.error("Vehicle owner does not match with maintenance vehicle customer info", vehicleInfo.getVehicleId());
                throw new ServiceException(ErrorCodeEnum.VEHICLE_OWNER_NOT_MATCHING);
            }*/
            vehicleInfo.setVehicle(vehicle);
            vehicle.setNextServiceAt(vehicleRequest.getNextServiceAt());
            vehicle.setNextServiceOn(vehicleRequest.getNextServiceOn());
            vehicleDao.save(vehicle);
        }
        maintenance.setVehicleInfo(vehicleInfo);
        maintenanceDao.save(maintenance);
        return vehicleInfo.getView(VehicleInfoView.class);
    }

    @Override
    @Transactional
    public CustomerInfoView updateMaintenanceCustomer(Long maintenanceId, CustomerRequest customerRequest) {
        Maintenance maintenance = maintenanceDao.findById(maintenanceId);
        if (maintenance == null) {
            log.error("Maintenance {} not found", maintenanceId);
            throw new ServiceException(ErrorCodeEnum.MAINTENANCE_NOT_FOUND);
        }
        CustomerInfo customerInfo = customerRequest.getEntity();
        if (customerInfo.getCustomerId() != null) {
            Customer customer = customerDao.findById(customerInfo.getCustomerId());
            if (customer == null) {
                log.error("Customer {} not found", customerInfo.getCustomerId());
                throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
            }
            customerInfo.setCustomer(customer);
        }
        maintenance.setCustomerInfo(customerInfo);
        maintenanceDao.save(maintenance);
        return customerInfo.getView(CustomerInfoView.class);
    }

    @Override
    @Transactional
    public MaintenanceView delete(Long id, Long userId) {
        Maintenance maintenance = maintenanceDao.findById(id);
        if (maintenance == null) {
            log.error("Maintenance {} not found", id);
            throw new ServiceException(ErrorCodeEnum.MAINTENANCE_NOT_FOUND);
        }
        maintenance.setStatus(EntityStatus.DELETED);
        maintenanceDao.save(maintenance);
        activityService.insert(Activity.builder()
                .category(Type.Category.MAINTENANCE)
                .activity(Type.Activity.DELETED)
                .activityFor(id)
                .userId(userId)
                .build());
        return maintenance.getView(MaintenanceView.class);
    }
}
