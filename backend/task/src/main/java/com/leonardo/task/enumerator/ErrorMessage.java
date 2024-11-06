package com.leonardo.task.enumerator;

public enum ErrorMessage {
    TASK_NOT_FOUND("Task not found"),
    INVALID_FIELDS("Invalid fields"),
    GENERIC("Internal server error"),
    TASK_WITH_TITLE_ALREADY_EXISTS("Task with title %s already exists");


    private final String messageTemplate;

    ErrorMessage(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessage(Object... params) {
        return String.format(this.messageTemplate, params);
    }
}
