package com.example.demo.exception;

public class SystemException extends RuntimeException {

    /** Serial version. */
    private static final long serialVersionUID = -4430803840375325775L;

    /**
     * Exception with message and throwable.
     * 
     * @param message type String
     * @param cause   type Throwable
     * @author KhoaNA
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception with only message.
     * 
     * @param message type String
     * @author KhoaNA
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * Exception with only throwable.
     * 
     * @param cause type Throwable
     * @author KhoaNA
     */
    public SystemException(Throwable cause) {
        super(cause);
    }
}
