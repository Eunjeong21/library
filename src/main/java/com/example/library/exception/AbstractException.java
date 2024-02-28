package com.example.library.exception;

public abstract class AbstractException extends RuntimeException {

    abstract public int getStatusCode();

    abstract public ErrorData getData();
}
