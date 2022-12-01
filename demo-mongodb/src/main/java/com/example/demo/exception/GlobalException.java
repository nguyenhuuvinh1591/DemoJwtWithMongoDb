package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends Exception {

    private static final long serialVersionUID = 2392926148247590423L;

    protected ExceptionCode exceptionCode;
}
