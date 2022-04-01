package com.motorcorp.application.modules.enquiry.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EnquiryUpdate {
    private String to;

    private String from;

    private String message;

    private Long by;

    /**
     * Kind of update <br>
     * Can be one of
     * <ul>
     * <li>ACTIVITY_NOTE</li>
     * <li>STATUS</li>
     * </ul>
     * and more...
     */
    @NotBlank
    private String kind;
}
