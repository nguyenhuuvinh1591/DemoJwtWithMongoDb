package com.example.demo.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DtsBaseResponse<T> {
//    private HttpStatus description;
    private int statusCode;

    private int code;
    private String message;

    private List<DtsApiError> errors;

    private T data;

    public DtsBaseResponse(int code, String messages) {
        this.code = code;
        this.message = messages;

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, DtsApiError error) {
        this.code = code;
        this.message = messages;
        if (this.errors == null) {
            this.errors = new ArrayList<>();
            this.errors.add(error);
        } else {
            this.errors.add(error);
        }

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, T data, DtsApiError error) {
        this.code = code;
        this.message = messages;
        this.data = data;
        if (this.errors == null) {
            this.errors = new ArrayList<>();
            this.errors.add(error);
        } else {
            this.errors.add(error);
        }

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, List<DtsApiError> errors) {
        this.code = code;
        this.message = messages;
        this.errors = errors;

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, T data, List<DtsApiError> errors) {
        this.code = code;
        this.message = messages;
        this.data = data;
        this.errors = errors;

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, T data) {
        this.code = code;
        this.message = messages;
        this.data = data;

        setDataHttpStatus();
    }

    public DtsBaseResponse(int code, String messages, T data, HttpStatus status) {
        this.code = code;
        this.message = messages;
        this.data = data;
//        this.description = status;
        this.statusCode = status.value();
    }

    public void setDataHttpStatus() {
        if (code == 1) {
            statusCode = HttpStatus.OK.value();
        } else if (code == 0) {
            statusCode = HttpStatus.OK.value();
            ;
        } else if (code == -1) {
            statusCode = HttpStatus.BAD_REQUEST.value();
        }

//        statusCode = description.value();
    }
}
