package com.motorcorp.application.modules.user.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordUpdate {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String repeatPassword;
}
