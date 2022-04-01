package com.motorcorp.application.modules.activities.model;

import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class ActivityView extends AbstractView {
    private Type.Category category;

    private Type.Activity activity;

    private Long activityFor;

    private Long activityId;

    private String from;

    private String to;

    private String message;

    private Long userId;

    private String userName;

    public void setUser(User user) {
        if (user == null) return;
        this.userName = user.getName();
    }
}
