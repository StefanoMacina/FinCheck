package com.macina.FinCheck.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MESSAGE_ENUM {

    SUCCESS("Success"),
    SERVER_ERROR("Internal server error"),
    NOT_FOUND("Not found"),
    ALREADY_EXIST("Already exist"),
    EMPTY_SET("Empty set");


    public final String msg;

    MESSAGE_ENUM(String msg) {
        this.msg = msg;
    }

    @JsonValue
    public String getMsg() {
        return msg;
    }
}