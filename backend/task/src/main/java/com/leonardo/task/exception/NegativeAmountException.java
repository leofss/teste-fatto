package com.leonardo.task.exception;

import com.leonardo.task.enumerator.ErrorMessage;

public class NegativeAmountException extends BaseCustomException{
    public NegativeAmountException(ErrorMessage errorMessage, Object... params) {
        super(errorMessage, params);
    }

}