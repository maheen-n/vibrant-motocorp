package com.motorcorp.application.modules.enquiry.dao.impl;

import com.motorcorp.application.modules.customer.dto.Customer;
import com.motorcorp.application.modules.enquiry.dao.EnquiryDao;
import com.motorcorp.application.modules.enquiry.dao.EnquirySpecifications;
import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.application.modules.enquiry.models.EnquiryFilter;
import com.motorcorp.models.BaseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EnquiryDaoImpl extends EnquirySpecifications<Enquiry> implements EnquiryDao {
    @Autowired
    private EnquiryRepository enquiryRepo;

    @Override
    public void save(Enquiry enquiry) {
        enquiryRepo.save(enquiry);
    }

    @Override
    public Enquiry findById(Long id) {
        return enquiryRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Enquiry> findAll(EnquiryFilter filter) {
        Specification<Enquiry> specification = Specification.where(
                query(filter.getQuery())
                        .and(date(filter.getDateFilter()))
                        .and(entityStatus(filter.getStatus()))
                        .and(include(filter.getInclude()))
                        .and(exclude(filter.getExclude()))
                        .and(enquiry(filter.getEnquiry()))
                        .and(enquiryStatus(filter.getEnquiryStatus()))
        );
        return enquiryRepo.findAll(specification, filter.filterPageable());
    }
}
