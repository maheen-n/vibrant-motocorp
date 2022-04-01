package com.motorcorp.application.modules.enquiry.dao.impl;

import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, JpaSpecificationExecutor<Enquiry> {
}
