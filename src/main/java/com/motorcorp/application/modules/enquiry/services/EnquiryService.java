package com.motorcorp.application.modules.enquiry.services;

import com.motorcorp.application.modules.enquiry.models.EnquiryCreateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryFilter;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdate;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryView;
import com.motorcorp.application.modules.vehicles.models.VehicleCreateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleUpdateRequest;
import com.motorcorp.application.modules.vehicles.models.VehicleView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface EnquiryService {
    EnquiryView create(EnquiryCreateRequest request);

    EnquiryView get(Long id);

    PaginatedList<EnquiryView> get(EnquiryFilter filter);

    EnquiryView update(Long id, EnquiryUpdateRequest updateRequest);

    void updateStatus(Long id, EnquiryUpdate update);

    void updateActivity(Long id, EnquiryUpdate update);

    EnquiryView delete(Long id, Long userId);
}
