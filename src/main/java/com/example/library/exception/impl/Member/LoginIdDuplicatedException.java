package com.example.library.exception.impl.Member;

import com.example.library.exception.AbstractException;
import com.example.library.exception.ErrorData;
import com.example.library.exception.MemberErrorCode;
import org.springframework.http.HttpStatus;

public class LoginIdDuplicatedException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public ErrorData getData() {
        return new ErrorData(MemberErrorCode.LOGIN_ID_DUPLICATED_EXCEPTION.name());
    }
}
