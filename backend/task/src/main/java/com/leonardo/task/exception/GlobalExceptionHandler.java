package com.leonardo.task.exception;

import com.leonardo.task.enumerator.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessageBuilder> handleTaskNotFoundException(TaskNotFoundException ex,
                                                                             HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageBuilder(
                        request,
                        HttpStatus.NOT_FOUND,
                        ex.getErrorMessage(),
                        ex.getParams()
                ));
    }


    @ExceptionHandler(TaskWithTitleAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageBuilder> handleTaskWithTitleAlreadyExistsException(
            TaskWithTitleAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageBuilder(
                        request,
                        HttpStatus.CONFLICT,
                        ex.getErrorMessage(),
                        ex.getParams()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageBuilder> handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                                     BindingResult bindingResult) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageBuilder(
                        request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        ErrorMessage.INVALID_FIELDS,
                        bindingResult
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageBuilder> handleGenericException(
            HttpServletRequest request,
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageBuilder(
                        request,
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorMessage.GENERIC,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageBuilder> handleHttpMessageNotReadableException(
            HttpServletRequest request,
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageBuilder(
                        request,
                        HttpStatus.BAD_REQUEST,
                        ErrorMessage.INVALID_FIELDS,
                        ex.getMessage()
                ));
    }
}
