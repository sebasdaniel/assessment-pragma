package com.pragma.plazoleta.user.infrastructure.exceptionhandler;

import com.pragma.plazoleta.user.domain.exception.InvalidFormatException;
import com.pragma.plazoleta.user.domain.exception.MissingDataException;
import com.pragma.plazoleta.user.infrastructure.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(MissingDataException.class)
    public ResponseEntity<Map<String, String>> handleMissingDataException(MissingDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ex.getMessage()));
    }
    
}