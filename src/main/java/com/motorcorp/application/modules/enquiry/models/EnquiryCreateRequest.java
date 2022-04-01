package com.motorcorp.application.modules.enquiry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.dto.AbstractRequestEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class EnquiryCreateRequest extends AbstractRequestEntity<Enquiry> {
    @NotBlank
    @Pattern(regexp = REGEX_NAME, message = REGEX_NAME_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    private String email;
    private String comment;
    @NotNull
    private Type.Enquiry enquiry;

    private String enquiryFor;

    private Long customerId;

    @JsonIgnore
    private Long initiatedId;
}
