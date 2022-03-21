package com.scarike.gp.web.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse<E> {
    private int status;
    private String message;
    private E data;

    // region Getter And Setter

    public int getStatus() {
        return status;
    }

    public SimpleResponse<E> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SimpleResponse<E> setMessage(String message) {
        this.message = message;
        return this;
    }

    public E getData() {
        return data;
    }

    public SimpleResponse<E> setData(E data) {
        this.data = data;
        return this;
    }

    // endregion

    @Override
    public String toString() {
        return "{\"status\":" + status +
                ", \"message\":\"" + message + '"' +
                ", \"payload\":" + data +
                '}';
    }

    public static <T> SimpleResponse<T> ok(T payload) {
        return new SimpleResponse<T>().setStatus(0).setMessage("OK").setData(payload);
    }

    public static <T> SimpleResponse<T> fail(String message) {
        return new SimpleResponse<T>().setStatus(1).setMessage(message);
    }

    public static <T> SimpleResponse<T> fail() {
        return new SimpleResponse<T>().setStatus(1).setMessage("UNKNOWN_FAILURE");
    }

    public static <T> SimpleResponse<T> notFound() {
        return new SimpleResponse<T>().setStatus(404).setMessage("NOT FOUND");
    }

    public static <T> SimpleResponse<T> forbidden() {
        return new SimpleResponse<T>().setStatus(403).setMessage("FORBIDDEN");
    }

    public static <T> SimpleResponse<T> badRequest() {
        return new SimpleResponse<T>().setStatus(400).setMessage("BAD REQUEST");
    }

    public static <T> SimpleResponse<T> serverError() {
        return new SimpleResponse<T>().setStatus(500).setMessage("服务器开小差啦:(");
    }

}
