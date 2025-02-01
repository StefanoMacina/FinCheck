package com.macina.FinCheck.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseData<T> {
    private ResponseBody<T> response;

    public ResponseData(T data, String msg, Integer code, Object entityError) {
        response = ResponseBody.<T>builder()
                .data(data)
                .msg(msg)
                .code(code)
                .entityError(entityError)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class ResponseBody<T> {
        private T data;
        private String msg;
        private Integer code;
        private Object entityError;
    }
}

