package com.motorcorp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motorcorp.util.JsonUtil;

import java.io.Serializable;

public abstract class CommonsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    @JsonIgnore
    public String toString() {
        return JsonUtil.toString(this);
    }

}
