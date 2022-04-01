package com.motorcorp.application.modules.customer.services.impl;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.activities.dto.Activity;
import com.motorcorp.application.modules.activities.services.ActivityService;
import com.motorcorp.application.modules.customer.dao.CustomerDao;
import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.customer.models.CustomerCreateRequest;
import com.motorcorp.application.modules.customer.models.CustomerFilter;
import com.motorcorp.application.modules.customer.models.CustomerUpdateRequest;
import com.motorcorp.application.modules.customer.models.CustomerView;
import com.motorcorp.application.modules.customer.services.CustomerService;
import com.motorcorp.enums.CommonErrorCodeEnum;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.exceptions.ServiceException;
import com.motorcorp.models.PaginatedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static com.motorcorp.application.exceptions.ErrorCodeEnum.CUSTOMER_NOT_FOUND;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public CustomerView create(CustomerCreateRequest request) {
        Customer customer = request.getEntity();
        customer.setStatus(EntityStatus.ACTIVE);
        customerDao.save(customer);
        activityService.insert(Activity.builder()
                .category(Type.Category.CUSTOMER)
                .activity(Type.Activity.CREATED)
                .activityFor(customer.getId())
                .userId(request.getInitiatedId())
                .build());
        return customer.getView(CustomerView.class);
    }

    @Override
    public CustomerView get(Long id) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            log.info("customer fetch for {} not found", id);
            throw new ServiceException(CUSTOMER_NOT_FOUND);
        }
        return customer.getView(CustomerView.class);
    }

    @Override
    public PaginatedList<CustomerView> get(CustomerFilter filter) {
        Page<Customer> page = customerDao.findAll(filter);
        return new PaginatedList<>(page, CustomerView.class);
    }

    @Override
    @Transactional
    public CustomerView update(Long id, CustomerUpdateRequest updateRequest) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            log.info("customer fetch for {} not found", id);
            throw new ServiceException(CUSTOMER_NOT_FOUND);
        }
        int hash = customer.hashCode();
        updateRequest.update(customer);
        if (hash == customer.hashCode()) throw new ServiceException(CommonErrorCodeEnum.NOT_MODIFIED);
        customerDao.save(customer);
        return customer.getView(CustomerView.class);
    }

    @Override
    @Transactional
    public CustomerView delete(Long id, Long userId) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            log.info("customer fetch for {} not found", id);
            throw new ServiceException(CUSTOMER_NOT_FOUND);
        }
        customer.setStatus(EntityStatus.DELETED);
        customerDao.save(customer);
        activityService.insert(Activity.builder()
                .category(Type.Category.CUSTOMER)
                .activity(Type.Activity.DELETED)
                .activityFor(id)
                .userId(userId)
                .build());
        return customer.getView(CustomerView.class);
    }

    public static void main(String[] args) {

        LocalTime bookingStartTime = LocalTime.of(11, 00);
        LocalTime bookingEndTime = LocalTime.of(12, 00);

        LocalTime slotstartTime = LocalTime.of(10, 00);
        LocalTime slotendTime = LocalTime.of(11, 00);
        if (slotstartTime.isBefore(bookingEndTime) && slotendTime.isAfter(bookingStartTime)) {
            System.out.println("Slot 10-11 not avilabele");
        } else System.out.println("Slot 10-11 available");
        slotstartTime = LocalTime.of(10, 30);
        slotendTime = LocalTime.of(11, 30);
        if (slotstartTime.isBefore(bookingEndTime) && slotendTime.isAfter(bookingStartTime)) {
            System.out.println("Slot 10 :30-11 :30 not avilabele");
        } else System.out.println("Slot 10 :30-11 :30 available");
       /* if (slotstartTime.compareTo(bookingStartTime) >= 0 && slotendTime.compareTo(bookingEndTime) <= 0) {
            System.out.println("Slot 10-11 available");
        }*/
    }
}
