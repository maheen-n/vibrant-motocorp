package com.motorcorp.application.modules.user.models;

import com.motorcorp.application.enums.Role;
import com.motorcorp.models.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView extends AbstractView {
    private String name;

    private String phone;

    private String email;

    private String gender;

    private Role role;

    private String userImage;

    private String dob;
}
