package com.motorcorp.application.modules.user.models;

import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBasicView extends AbstractView {
    private String name;

    private String email;

    private String phone;
}
