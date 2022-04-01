package com.motorcorp.application.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Type {
    public enum Enquiry {
        VEHICLE, RIDING_GEAR, ACCESSORIES, SPARE_PARTS
    }

    public enum Ticket {
        VEHICLE, RIDING_GEAR, ACCESSORIES, SPARE_PARTS
    }

    public enum Service {
        FREE, PAID
    }

    public enum Category {
        VEHICLE, CUSTOMER, MAINTENANCE, ENQUIRY, TICKET
    }

    public enum Activity {
        CREATED, STATUS, NOTE, CLOSED, REOPEN, DELETED,
        VEHICLE_ADDED, TICKET_RAISED, ENQUIRY_RAISED, MAINTENANCE_VEHICLE
    }
}
