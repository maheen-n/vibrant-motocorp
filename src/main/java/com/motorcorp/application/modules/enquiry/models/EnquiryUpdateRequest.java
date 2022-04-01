package com.motorcorp.application.modules.enquiry.models;

import com.motorcorp.application.enums.Status;
import com.motorcorp.application.enums.Type;
import com.motorcorp.application.modules.enquiry.dto.Enquiry;
import com.motorcorp.dto.AbstractRequestUpdateEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.motorcorp.util.RegexConstants.REGEX_NAME;
import static com.motorcorp.util.RegexConstants.REGEX_NAME_MESSAGE;
import static com.motorcorp.util.RegexConstants.REGEX_PHONE;

@Getter
@Setter
public class EnquiryUpdateRequest extends AbstractRequestUpdateEntity<Enquiry> {
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

    @Override
    public Enquiry update(Enquiry entity) {
        if (name != null) entity.setName(name);
        if (phone != null) entity.setPhone(phone);
        if (email != null) entity.setEmail(email);
        if (comment != null) entity.setComment(comment);
        if (enquiry != null) entity.setEnquiry(enquiry);
        if (enquiryFor != null) entity.setEnquiryFor(enquiryFor);
        return entity;
    }
}
