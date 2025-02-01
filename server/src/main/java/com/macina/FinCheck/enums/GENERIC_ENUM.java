package com.macina.FinCheck.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GENERIC_ENUM {

    dataKey("data");

    public final String msg;

    GENERIC_ENUM(String msg) {
        this.msg = msg;
    }

    @JsonValue
    public String getMsg() {
        return msg;
    }
}