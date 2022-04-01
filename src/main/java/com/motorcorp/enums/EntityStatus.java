package com.motorcorp.enums;

import lombok.Getter;

public enum EntityStatus {
    ACTIVE(10), INACTIVE(20), CANCELLED(80), DELETED(100);

    @Getter
    private final int val;

    EntityStatus(int val) {
        this.val = val;
    }
}
