package com.example.library.exception.impl.Auth;

import com.example.library.exception.AbstractException;
import com.example.library.exception.AuthErrorCode;
import com.example.library.exception.ErrorData;
import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public ErrorData getData() {
        return new ErrorData(AuthErrorCode.AUTHENTICATION_FAILED_EXCEPTION.name());
    }
}
