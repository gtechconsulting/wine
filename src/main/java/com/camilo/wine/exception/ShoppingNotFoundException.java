package com.camilo.wine.exception;

public class ShoppingNotFoundException extends RuntimeException{

    private Throwable erroThrowable;

    public ShoppingNotFoundException() {
    }

    public ShoppingNotFoundException(String msg) {
        super(msg);
    }

    public ShoppingNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ShoppingNotFoundException(Throwable cause) {
        super(cause);
    }

    public Throwable getErroThrowable() {
        return erroThrowable;
    }

    public void setErroThrowable(Throwable erroThrowable) {
        this.erroThrowable = erroThrowable;
    }
}
