package com.motorcorp.application.modules.models.services.impl;

import com.motorcorp.application.modules.models.dao.VehicleModelDao;
import com.motorcorp.application.modules.models.dto.VehicleModel;
import com.motorcorp.application.modules.models.models.VehicleModelCreateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelFilter;
import com.motorcorp.application.modules.models.models.VehicleModelUpdateRequest;
import com.motorcorp.application.modules.models.models.VehicleModelView;
import com.motorcorp.application.modules.models.services.VehicleModelService;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.motorcorp.application.exceptions.ErrorCodeEnum.MODEL_NOT_FOUND;
import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_EMAIL_ALREADY_EXISTS;
import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_NOT_FOUND;
import static com.motorcorp.application.exceptions.ErrorCodeEnum.USER_PHONE_ALREADY_EXISTS;

@Slf4j
@Service
public class VehicleModelServiceImpl implements VehicleModelService {
    @Autowired
    private VehicleModelDao vehicleModelDao;

    @Transactional
    @Override
    public VehicleModelView create(VehicleModelCreateRequest request) {
        VehicleModel vehicleModel = request.getEntity();
        vehicleModel.setStatus(EntityStatus.ACTIVE);
        vehicleModelDao.save(vehicleModel);
        return vehicleModel.getView(VehicleModelView.class);
    }

    @Transactional(readOnly = true)
    @Override
    public VehicleModelView get(Long id) {
        VehicleModel vehicleModel = vehicleModelDao.findById(id);
        if (vehicleModel == null) {
            log.info("Model fetch for {} not found", id);
            throw new ServiceException(MODEL_NOT_FOUND);
        }
        return vehicleModel.getView(VehicleModelView.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedList<VehicleModelView> get(VehicleModelFilter filter) {
        Page<VehicleModel> page = vehicleModelDao.findAll(filter);
        return new PaginatedList<>(page, VehicleModelView.class);
    }

    @Transactional
    @Override
    public VehicleModelView update(Long id, VehicleModelUpdateRequest updateRequest) {
        VehicleModel vehicleModel = vehicleModelDao.findById(id);
        if (vehicleModel == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(MODEL_NOT_FOUND);
        }
        int hash = vehicleModel.hashCode();
        updateRequest.update(vehicleModel);
        if (hash == vehicleModel.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        vehicleModelDao.save(vehicleModel);
        return vehicleModel.getView(VehicleModelView.class);
    }

    @Transactional
    @Override
    public VehicleModelView delete(Long id) {
        VehicleModel vehicleModel = vehicleModelDao.findById(id);
        if (vehicleModel == null) {
            log.info("user fetch for {} not found", id);
            throw new ServiceException(MODEL_NOT_FOUND);
        }
        vehicleModel.setStatus(EntityStatus.DELETED);
        vehicleModelDao.save(vehicleModel);
        return vehicleModel.getView(VehicleModelView.class);
    }
}
