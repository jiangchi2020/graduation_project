package com.scarike.gp.crawler.exception;

public class PoiSearchException extends RuntimeException{
    public PoiSearchException() {
    }

    public PoiSearchException(String message) {
        super(message);
    }

    public PoiSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoiSearchException(Throwable cause) {
        super(cause);
    }

    public PoiSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
