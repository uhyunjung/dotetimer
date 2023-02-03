package com.dotetimer.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {
    // Response 생성
    private ResponseEntity<ErrorResponse> newResponse(String message, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .error(status.name())
                .message(message)
                .build();
        return new ResponseEntity<>(errorResponse, httpHeaders, status);
    }

    // CustomException 처리
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.info(e.getMessage());
        return newResponse(e.getErrorCode().getDetail(), e.getErrorCode().getHttpStatus());
    }

    // 400 Bad Request 처리
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class, DuplicateKeyException.class})
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.info(e.getMessage());
        if (e instanceof MethodArgumentNotValidException) {
            return newResponse(((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return newResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found 처리
    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        log.info(e.getMessage());
        return newResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 404 Method Not Allowed 에러
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        log.info(e.getMessage());
        return newResponse(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415 Unsupported Media Type 에러
    @ExceptionHandler({HttpMediaTypeException.class})
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        log.info(e.getMessage());
        return newResponse(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500 Internal Server Error 에러
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.info(e.getMessage());
        return newResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
