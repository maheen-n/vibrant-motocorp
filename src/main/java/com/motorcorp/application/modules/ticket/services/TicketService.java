package com.motorcorp.application.modules.ticket.services;

import com.motorcorp.application.modules.enquiry.models.EnquiryCreateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryUpdateRequest;
import com.motorcorp.application.modules.enquiry.models.EnquiryView;
import com.motorcorp.application.modules.ticket.models.TicketCreateRequest;
import com.motorcorp.application.modules.ticket.models.TicketFilter;
import com.motorcorp.application.modules.ticket.models.TicketUpdate;
import com.motorcorp.application.modules.ticket.models.TicketUpdateRequest;
import com.motorcorp.application.modules.ticket.models.TicketView;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;

public interface TicketService {
    TicketView create(TicketCreateRequest request);

    TicketView get(Long id);

    PaginatedList<TicketView> get(TicketFilter filter);

    TicketView update(Long id, TicketUpdateRequest updateRequest);

    void updateStatus(Long id, TicketUpdate update);

    void updateActivity(Long id, TicketUpdate update);

    TicketView delete(Long id, Long userId);
}
