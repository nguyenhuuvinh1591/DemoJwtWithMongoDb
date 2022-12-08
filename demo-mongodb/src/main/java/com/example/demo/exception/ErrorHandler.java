package com.example.demo.exception;

import org.springframework.validation.BindingResult;

import com.example.demo.common.DtsApiResponse;

public interface ErrorHandler {

    public DtsApiResponse handlerException(Exception ex, long start);

    public DtsApiResponse handlerException(Object data, long start);

    public DtsApiResponse handlerBindingResult(BindingResult bindingResult, long start);

    public DtsApiResponse handlerUnauthorize();
}
