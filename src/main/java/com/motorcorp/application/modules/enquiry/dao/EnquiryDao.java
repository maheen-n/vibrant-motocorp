package com.motorcorp.application.modules.enquiry.dao;

import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.application.modules.enquiry.models.EnquiryFilter;
import com.motorcorp.application.modules.vehicles.dto.Vehicle;
import com.motorcorp.models.BaseFilter;
import org.springframework.data.domain.Page;

public interface EnquiryDao {
    void save(Enquiry enquiry);

    Enquiry findById(Long id);

    Page<Enquiry> findAll(EnquiryFilter filter);
}
