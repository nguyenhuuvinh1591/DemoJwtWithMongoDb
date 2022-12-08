package com.example.demo.exception.imlp;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.example.demo.common.DtsApiResponse;
import com.example.demo.exception.SuccessHandler;

public class SuccessHandlerImpl implements SuccessHandler {

    public static final int SUCCESS_CODE = 1;
    public static final String SUCCESS = "success";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    public DtsApiResponse handlerSuccess(Object data, long start) {
        long took = System.currentTimeMillis() - start;
        return new DtsApiResponse(SUCCESS_CODE, SUCCESS, data);
    }

    public DtsApiResponse handlerSuccessAdmin(Object data) {
        return new DtsApiResponse(SUCCESS_CODE, SUCCESS, data);
    }

    public DtsApiResponse handlerSuccessWithMsg(Object data, Integer statusCode, long start, String msg) {
        String messages = messageSource.getMessage(msg, null,
                new Locale(Optional.ofNullable(this.request.getHeader(ACCEPT_LANGUAGE)).orElse("vi")));
        long took = System.currentTimeMillis() - start;
        return new DtsApiResponse(SUCCESS_CODE, messages, data);
    }
}
