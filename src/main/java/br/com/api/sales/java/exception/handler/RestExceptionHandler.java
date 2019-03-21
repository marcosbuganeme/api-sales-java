package br.com.api.sales.java.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.exception.builder.ErrorDetails;
import br.com.api.sales.java.exception.builder.ResourceNotFoundDetails;
import br.com.api.sales.java.exception.builder.ValidationErrorDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {

        ResourceNotFoundDetails details = ResourceNotFoundDetails
                                                .Builder
                                                .newBuilder()
                                                .timestamp(new Date().getTime())
                                                .status(HttpStatus.NOT_FOUND.value())
                                                .title("Resource not found")
                                                .detail(exception.getMessage())
                                                .developerMessage(exception.getClass().getName())
                                                .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

        List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();

        String fields = fieldErrors
                            .stream()
                            .map(FieldError::getField)
                            .collect(Collectors.joining(","));

        String fieldMessages = fieldErrors
                                    .stream()
                                    .map(FieldError::getDefaultMessage)
                                    .collect(Collectors.joining(","));

        ValidationErrorDetails rnfDetails = ValidationErrorDetails
                                                .Builder
                                                .newBuilder()
                                                .timestamp(new Date().getTime())
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .title("Validation Error")
                                                .detail("Field Validation Error")
                                                .developerMessage(manvException.getClass().getName())
                                                .field(fields)
                                                .fieldMessage(fieldMessages)
                                                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        ErrorDetails errorDetails = ErrorDetails
                                        .Builder
                                        .newBuilder()
                                        .timestamp(new Date().getTime())
                                        .status(status.value())
                                        .title("Internal Exception")
                                        .detail(exception.getMessage())
                                        .developerMessage(exception.getClass().getName())
                                        .build();

        return new ResponseEntity<>(errorDetails, headers, status);
    }
}