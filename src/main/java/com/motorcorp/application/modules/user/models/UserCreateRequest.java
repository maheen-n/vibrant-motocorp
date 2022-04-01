package com.motorcorp.application.modules.user.models;

import com.motorcorp.application.enums.Role;
import com.motorcorp.application.modules.user.dto.User;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_EMAIL;
import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class UserCreateRequest extends AbstractRequestEntity<User> {
    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    @Pattern(regexp = REGEX_EMAIL)
    private String email;

    private String gender;

    @NotNull
    private Role role;

    private String userImage;

    private String dob;

    private String userName;

    @NotBlank
    private String password;
}
