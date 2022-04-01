package com.motorcorp.application.modules.user.models;


import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_EMAIL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class UserUpdateRequest extends AbstractRequestUpdateEntity<User> {

    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    @Pattern(regexp = REGEX_EMAIL)
    private String email;

    private String gender;
    @NotNull
    private Role role;

    private String userImage;

    private String dob;

    @Override
    public User update(User entity) {
        if (name != null) entity.setName(name);
        if (phone != null) entity.setPhone(phone);
        if (email != null) entity.setEmail(email);
        if (gender != null) entity.setGender(name);
        if (role != null) entity.setRole(role);
        if (dob != null) entity.setDob(dob);
        return entity;
    }
}
