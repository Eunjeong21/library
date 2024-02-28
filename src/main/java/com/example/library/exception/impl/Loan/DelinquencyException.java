package com.example.library.exception.impl.Loan;

import com.example.library.exception.AbstractException;
import com.example.library.exception.ErrorData;
import com.example.library.exception.LoanErrorCode;
import org.springframework.http.HttpStatus;

public class DelinquencyException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public ErrorData getData() {
        return new ErrorData(LoanErrorCode.DELINQUENCY_EXCEPTION.name());
    }
}
