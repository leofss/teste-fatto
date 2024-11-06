package com.leonardo.task.exception;

import com.leonardo.task.enumerator.ErrorMessage;

public class TaskWithTitleAlreadyExistsException extends BaseCustomException{
    public TaskWithTitleAlreadyExistsException(ErrorMessage errorMessage, Object... params) {
        super(errorMessage, params);
    }

}
