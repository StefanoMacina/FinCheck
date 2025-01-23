package com.macina.FinCheck.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseData<T> {

    private T data;

    private String msg;

    private Integer code;

    private Object entityError;


}
