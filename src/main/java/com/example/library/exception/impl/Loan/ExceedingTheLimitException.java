package com.example.library.exception.impl.Loan;

import com.example.library.exception.AbstractException;
import com.example.library.exception.ErrorData;
import com.example.library.exception.LoanErrorCode;
import org.springframework.http.HttpStatus;

public class ExceedingTheLimitException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public ErrorData getData() {
        return new ErrorData(LoanErrorCode.EXCEEDING_THE_LIMIT_EXCEPTION.name());
    }
}
