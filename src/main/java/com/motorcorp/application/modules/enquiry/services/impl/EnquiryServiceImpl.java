package com.motorcorp.application.modules.enquiry.services.impl;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.exceptions.ErrorCodeEnum;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.enquiry.dao.EnquiryDao;
import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.application.modules.enquiry.models.EnquiryCreateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryFilter;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdate;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryView;
import com.motorcorp.application.modules.enquiry.services.EnquiryService;
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

@Slf4j
@Service
public class EnquiryServiceImpl implements EnquiryService {
    @Autowired
    private EnquiryDao enquiryDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public EnquiryView create(EnquiryCreateRequest request) {
        Enquiry enquiry = request.getEntity();
        if (request.getCustomerId() != null) {
            Customer customer = customerDao.findById(enquiry.getCustomerId());
            if (customer == null) {
                log.error("Customer not found for id {}", request.getCustomerId());
                throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
            }
            enquiry.setCustomer(customer);
        }
        enquiry.setStatus(EntityStatus.ACTIVE);
        enquiry.setEnquiryStatus(Status.Enquiry.NEW);
        enquiryDao.save(enquiry);
        activityService.insert(Activity.builder()
                .category(Type.Category.ENQUIRY)
                .activity(Type.Activity.CREATED)
                .activityFor(enquiry.getId())
                .userId(request.getInitiatedId())
                .build());
        if (enquiry.getCustomer() != null) {
            activityService.insert(Activity.builder()
                    .category(Type.Category.CUSTOMER)
                    .activity(Type.Activity.ENQUIRY_RAISED)
                    .activityFor(enquiry.getCustomer().getId())
                    .activityId(enquiry.getId())
                    .build());
        }
        return enquiry.getView(EnquiryView.class);
    }

    @Override
    public EnquiryView get(Long id) {
        Enquiry enquiry = enquiryDao.findById(id);
        if (enquiry == null) {
            log.error("Enquiry not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.ENQUIRY_NOT_FOUND);
        }
        return enquiry.getView(EnquiryView.class);
    }

    @Override
    public PaginatedList<EnquiryView> get(EnquiryFilter filter) {
        Page<Enquiry> page = enquiryDao.findAll(filter);
        return new PaginatedList<>(page, EnquiryView.class);
    }

    @Override
    @Transactional
    public EnquiryView update(Long id, EnquiryUpdateRequest updateRequest) {
        Enquiry enquiry = enquiryDao.findById(id);
        if (enquiry == null) {
            log.error("Enquiry not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.ENQUIRY_NOT_FOUND);
        }
        if (updateRequest.getCustomerId() != null) {
            Customer customer = customerDao.findById(enquiry.getCustomerId());
            if (customer == null) {
                log.error("Customer not found for id {}", updateRequest.getCustomerId());
                throw new ServiceException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
            }
            enquiry.setCustomer(customer);
        }
        int hash = enquiry.hashCode();
        updateRequest.update(enquiry);
        if (hash == enquiry.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        enquiryDao.save(enquiry);
        return enquiry.getView(EnquiryView.class);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, EnquiryUpdate update) {
        Status.Enquiry status = Status.Enquiry.valueOf(update.getTo());
        Enquiry enquiry = enquiryDao.findById(id);
        if (enquiry == null) {
            log.error("Enquiry not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.ENQUIRY_NOT_FOUND);
        }
        Status.Enquiry oldStatus = enquiry.getEnquiryStatus();
        if (status == oldStatus) {
            log.info("No change in status found. exiting...");
            return;
        }
        enquiry.setEnquiryStatus(status);
        enquiryDao.save(enquiry);
        activityService.insert(Activity.builder()
                .category(Type.Category.ENQUIRY)
                .activity(Type.Activity.STATUS)
                .activityFor(enquiry.getId())
                .from(oldStatus.toString())
                .to(status.toString())
                .userId(update.getBy())
                .build());
    }

    @Override
    @Transactional
    public void updateActivity(Long id, EnquiryUpdate update) {
        Enquiry enquiry = enquiryDao.findById(id);
        if (enquiry == null) {
            log.error("Enquiry not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.ENQUIRY_NOT_FOUND);
        }
        activityService.insert(Activity.builder()
                .category(Type.Category.ENQUIRY)
                .activity(Type.Activity.NOTE)
                .message(update.getMessage())
                .activityFor(id)
                .userId(update.getBy())
                .build());
    }

    @Override
    @Transactional
    public EnquiryView delete(Long id, Long userId) {
        Enquiry enquiry = enquiryDao.findById(id);
        if (enquiry == null) {
            log.error("Enquiry not found for id {}", id);
            throw new ServiceException(ErrorCodeEnum.ENQUIRY_NOT_FOUND);
        }
        enquiry.setStatus(EntityStatus.DELETED);
        enquiryDao.save(enquiry);
        activityService.insert(Activity.builder()
                .category(Type.Category.ENQUIRY)
                .activity(Type.Activity.DELETED)
                .activityFor(id)
                .userId(userId)
                .build());
        return enquiry.getView(EnquiryView.class);
    }
}
