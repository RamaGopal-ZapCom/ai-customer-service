package com.zapcom.advice;
import com.zapcom.constants.CustomerServiceConstants;
import com.zapcom.exceptions.CustomerServiceException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomerServiceAdvice {
    @ExceptionHandler(CustomerServiceException.class)
    public ResponseEntity<Object> handleCustomerServiceException(CustomerServiceException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, CustomerServiceConstants.BAD_REQUEST_MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, CustomerServiceConstants.BAD_REQUEST_MESSAGE, errorMessages);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, CustomerServiceConstants.INTERNAL_SERVER_ERROR_MESSAGE, ex.getMessage());
    }
    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put(CustomerServiceConstants.TIMESTAMP, LocalDateTime.now());
        body.put(CustomerServiceConstants.STATUS, status.value());
        body.put(CustomerServiceConstants.ERROR, error);
        body.put(CustomerServiceConstants.MESSAGE, message);
        return new ResponseEntity<>(body, status);
    }
}
