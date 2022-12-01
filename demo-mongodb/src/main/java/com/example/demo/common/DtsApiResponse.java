package com.example.demo.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DtsApiResponse<T> extends DtsBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -2080447665304438927L;

    public DtsApiResponse() {

    }

    /**
     * Constructor
     *
     * @param code
     * @param message
     * @param description
     * @param took
     */
    public DtsApiResponse(int code, String message) {
        super(code, message);
    }

    public DtsApiResponse(int code, String message, T data) {
        super(code, message, data);
    }

    public DtsApiResponse(int code, String message, T data, HttpStatus status) {
        super(code, message, data, status);
    }

    /**
     * Constructor for case Fail.
     *
     * @param code
     * @param messages
     * @param data
     */
    public DtsApiResponse(int code, String messages, T data, DtsApiError error) {
        super(code, messages, data, error);
    }

    /**
     * Constructor for case Fail.
     *
     * @param code
     * @param messages
     * @param data
     */
    public DtsApiResponse(int code, String description, T data, List<DtsApiError> errors) {
        super(code, description, data, errors);
    }
}
