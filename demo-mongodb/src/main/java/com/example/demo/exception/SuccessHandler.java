package com.example.demo.exception;

import com.example.demo.common.DtsApiResponse;

public interface SuccessHandler {

    public DtsApiResponse handlerSuccess(Object data, long start);

    public DtsApiResponse handlerSuccessAdmin(Object data);

    public DtsApiResponse handlerSuccessWithMsg(Object data, Integer statusCode, long start, String msg);

}
