package com.scarike.gp.web.poi.exception;

public class IncomprehensibleQueryException extends RuntimeException{

    public IncomprehensibleQueryException() {
    }

    public IncomprehensibleQueryException(String message) {
        super(message);
    }

    public IncomprehensibleQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncomprehensibleQueryException(Throwable cause) {
        super(cause);
    }

    public IncomprehensibleQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
