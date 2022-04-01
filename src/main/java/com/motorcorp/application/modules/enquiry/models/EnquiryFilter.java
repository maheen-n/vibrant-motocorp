package com.motorcorp.application.modules.enquiry.models;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.enums.EntityStatus;
import com.motorcorp.models.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnquiryFilter extends BaseFilter {
    private Type.Enquiry[] enquiry;

    private Long customerId;

    private Status.Enquiry[] enquiryStatus;

    public Status.Enquiry[] getEnquiryStatus() {
        return enquiryStatus == null || enquiryStatus.length == 0 ? new Status.Enquiry[]{Status.Enquiry.NEW, Status.Enquiry.IN_PROGRESS, Status.Enquiry.COMPLETED} : enquiryStatus;
    }

    public void setEnquiryStatus(Status.Enquiry enquiryStatus) {
        this.enquiryStatus = new Status.Enquiry[]{enquiryStatus};
    }

    public void setEnquiry(Type.Enquiry enquiry) {
        this.enquiry = new Type.Enquiry[]{enquiry};
    }
}
