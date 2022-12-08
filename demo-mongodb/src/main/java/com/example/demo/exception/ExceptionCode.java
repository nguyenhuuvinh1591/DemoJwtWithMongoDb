package com.example.demo.exception;

import com.example.demo.utils.DtsStringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionCode {
    public static final String UNDERLINED = "_";
    private String text;
    private int value;

    public ExceptionCode(String exceptionErrorCode) {
        int indexUnderlinedFirst = DtsStringUtil.indexOf(exceptionErrorCode, UNDERLINED);
        this.value = Integer.parseInt(DtsStringUtil.substring(exceptionErrorCode, 0, indexUnderlinedFirst));
        this.text = DtsStringUtil.substring(exceptionErrorCode, indexUnderlinedFirst + 1,
                DtsStringUtil.length(exceptionErrorCode));
    }
}
