package com.scarike.gp.web.common.grpc;

public class NLPCallException extends RuntimeException{
    public NLPCallException() {
    }

    public NLPCallException(String message) {
        super(message);
    }

    public NLPCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public NLPCallException(Throwable cause) {
        super(cause);
    }

    public NLPCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
