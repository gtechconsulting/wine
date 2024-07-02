package com.camilo.wine.exception;

public class ProductNotFoundException extends RuntimeException{

    private Throwable erroThrowable;

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }

    public ProductNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    public Throwable getErroThrowable() {
        return erroThrowable;
    }

    public void setErroThrowable(Throwable erroThrowable) {
        this.erroThrowable = erroThrowable;
    }
}
