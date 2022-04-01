package com.motorcorp.application.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
    public enum Maintenance {
        NEW(10), IN_PROGRESS(20), COMPLETED(30), CLOSED(40);

        @Getter
        private final int val;

        Maintenance(int val) {
            this.val = val;
        }
    }

    public enum Enquiry {
        NEW(10), IN_PROGRESS(20), COMPLETED(30), CLOSED(40);

        @Getter
        private final int val;

        Enquiry(int val) {
            this.val = val;
        }
    }

    public enum Ticket {
        NEW(10), IN_PROGRESS(20), COMPLETED(30), CLOSED(40);

        @Getter
        private final int val;

        Ticket(int val) {
            this.val = val;
        }
    }
}
