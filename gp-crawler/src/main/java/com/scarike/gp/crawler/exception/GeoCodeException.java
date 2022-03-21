package com.scarike.gp.crawler.exception;

public class GeoCodeException extends RuntimeException{
    public GeoCodeException() {
    }

    public GeoCodeException(String message) {
        super(message);
    }

    public GeoCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
