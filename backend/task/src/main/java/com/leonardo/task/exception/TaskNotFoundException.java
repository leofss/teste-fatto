package com.leonardo.task.exception;

import com.leonardo.task.enumerator.ErrorMessage;

public class TaskNotFoundException extends BaseCustomException{
    public TaskNotFoundException(ErrorMessage errorMessage, Object... params) {
        super(errorMessage, params);
    }

}
