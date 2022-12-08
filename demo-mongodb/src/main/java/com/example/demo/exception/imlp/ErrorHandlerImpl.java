package com.example.demo.exception.imlp;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.example.demo.common.DtsApiResponse;
import com.example.demo.exception.DetailException;
import com.example.demo.exception.ErrorHandler;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.exception.GlobalException;
import com.example.demo.utils.DtsCollectionUtil;
import com.example.demo.utils.DtsStringUtil;

public class ErrorHandlerImpl implements ErrorHandler {

    public static final String E500_ERROR_INTERNAL = "500_ERROR_INTERNAL";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final int ERROR_CODE = -1;
    public static final String ERROR = "error";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    public DtsApiResponse handlerException(Exception ex, long start) {
        // Default message
        long took = System.currentTimeMillis() - start;
        ExceptionCode expCode = new ExceptionCode(E500_ERROR_INTERNAL);
        boolean isTranslate = false;
        String message = ex.getMessage();
        Object[] paramater = null;

        if (ex instanceof GlobalException) {
            GlobalException globalException = (GlobalException) ex;
            expCode = globalException.getExceptionCode();
            if (ex instanceof DetailException) {
                DetailException detailException = (DetailException) globalException;
                isTranslate = detailException.isTranslate();
                message = detailException.getSpecificMsg();
                paramater = detailException.getParamater();
            }
        }

        String code = expCode.getText();
        int value = expCode.getValue();

        if (isTranslate) {
            message = this.messageSource.getMessage(code, paramater,
                    new Locale(Optional.ofNullable(this.request.getHeader(ACCEPT_LANGUAGE)).orElse("vi")));
        }

        if (StringUtils.isBlank(message)) {
            message = code;
        }

        if (value > 600) {
            value = 400;
        } else if (value > 500) {
            value = 500;
        } else if (value > 400) {
            value = 400;
        }

        DtsApiResponse rs = new DtsApiResponse(ERROR_CODE, message, null, HttpStatus.valueOf(value));
        return rs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.dts.exception.ErrorHandler#handlerBindingResult(java.lang.Object)
     */
    @Override
    public DtsApiResponse handlerBindingResult(BindingResult bindingResult, long start) {
        // Default message
        long took = System.currentTimeMillis() - start;

        String code = E500_ERROR_INTERNAL;
        String message = null;
        Object data = null;
        if (null != bindingResult && DtsCollectionUtil.isNotEmpty(bindingResult.getAllErrors())) {
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            code = bindingResult.getAllErrors().get(0).getCode();
            Object[] args = bindingResult.getAllErrors().get(0).getArguments();
            data = bindingResult.getAllErrors();

            if (DtsStringUtil.isBlank(code)) {
                code = E500_ERROR_INTERNAL;
            }

            ExceptionCode expCode = new ExceptionCode(code);
            message = this.messageSource.getMessage(expCode.getText(), args,
                    new Locale(Optional.ofNullable(this.request.getHeader(ACCEPT_LANGUAGE)).orElse("vi")));

            if (DtsStringUtil.isNotBlank(defaultMessage)) {
                message = message.concat(": ").concat(System.lineSeparator()).concat(defaultMessage);
            }
        }

        DtsApiResponse rs = new DtsApiResponse(ERROR_CODE, message, null, HttpStatus.INTERNAL_SERVER_ERROR);
        return rs;
    }

    @Override
    public DtsApiResponse handlerUnauthorize() {
        String message = this.messageSource.getMessage("ACCESS_DENIED", null,
                new Locale(Optional.ofNullable(this.request.getHeader(ACCEPT_LANGUAGE)).orElse("vi")));

        return new DtsApiResponse(ERROR_CODE, message, null, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public DtsApiResponse handlerException(Object data, long start) {
        long took = System.currentTimeMillis() - start;
        return new DtsApiResponse(ERROR_CODE, ERROR, data);
    }

}
