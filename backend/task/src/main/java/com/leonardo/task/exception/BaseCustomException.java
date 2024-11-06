package com.leonardo.task.exception;

import com.leonardo.task.enumerator.ErrorMessage;

public class BaseCustomException extends RuntimeException {
    private final ErrorMessage errorMessage;
    private final Object[] params;

    public BaseCustomException(ErrorMessage errorMessage, Object... params) {
        super(errorMessage.getMessage(params));
        this.errorMessage = errorMessage;
        this.params = params;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public Object[] getParams() {
        return params;
    }
}
