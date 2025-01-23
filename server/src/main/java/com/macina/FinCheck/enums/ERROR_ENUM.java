package com.macina.FinCheck.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ERROR_ENUM {

    SUCCESS(1),
    SERVER_ERROR(2),
    NOT_FOUND(3),
    ALREADY_EXIST(4),
    EMPTY_SET(5);
    public final Integer error;

    ERROR_ENUM(Integer error  ) {
        this.error = error;
    }

    @JsonValue
    public Integer getCode() {
        return error;
    }
}