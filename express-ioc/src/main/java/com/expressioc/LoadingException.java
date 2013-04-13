package com.expressioc;

public class LoadingException extends RuntimeException{
    public LoadingException(Exception e) {
        super(e);
    }
}
