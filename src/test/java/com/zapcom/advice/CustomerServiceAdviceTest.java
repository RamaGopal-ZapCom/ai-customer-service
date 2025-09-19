package com.zapcom.advice;

import com.zapcom.constants.CustomerServiceConstants;
import com.zapcom.exceptions.CustomerServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



class CustomerServiceAdviceTest {

    private CustomerServiceAdvice advice;
    @BeforeEach
    public void setUp() {
        advice = new CustomerServiceAdvice();
    }
    @Test
    public void testHandleCustomerServiceException() {
        CustomerServiceException ex = new CustomerServiceException("Customer not found");
        ResponseEntity<Object> response = advice.handleCustomerServiceException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = extractResponseBody(response);
        assertEquals(400, body.get(CustomerServiceConstants.STATUS));
        assertEquals(CustomerServiceConstants.BAD_REQUEST_MESSAGE, body.get(CustomerServiceConstants.ERROR));
        assertEquals("Customer not found", body.get(CustomerServiceConstants.MESSAGE));
        assertNotNull(body.get(CustomerServiceConstants.TIMESTAMP));
    }
    @Test
    public void testHandleValidationException() {
        FieldError fieldError1 = new FieldError("objectName", "field1", "must not be null");
        FieldError fieldError2 = new FieldError("objectName", "field2", "must be a valid email");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
        ResponseEntity<Object> response = advice.handleValidationException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = extractResponseBody(response);
        assertEquals(400, body.get(CustomerServiceConstants.STATUS));
        assertTrue(((String) body.get(CustomerServiceConstants.MESSAGE)).contains("field1: must not be null"));
        assertTrue(((String) body.get(CustomerServiceConstants.MESSAGE)).contains("field2: must be a valid email"));
        assertEquals(CustomerServiceConstants.BAD_REQUEST_MESSAGE, body.get(CustomerServiceConstants.ERROR));
        assertNotNull(body.get(CustomerServiceConstants.TIMESTAMP));
    }
    @Test
    public void testHandleGenericException() {
        Exception ex = new Exception("Something went wrong");
        ResponseEntity<Object> response = advice.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = extractResponseBody(response);
        assertEquals(500, body.get(CustomerServiceConstants.STATUS));
        assertEquals(CustomerServiceConstants.INTERNAL_SERVER_ERROR_MESSAGE, body.get(CustomerServiceConstants.ERROR));
        assertEquals("Something went wrong", body.get(CustomerServiceConstants.MESSAGE));
        assertNotNull(body.get(CustomerServiceConstants.TIMESTAMP));
    }
    private Map<String, Object> extractResponseBody(ResponseEntity<Object> response) {
        assertNotNull(response);
        Object body = response.getBody();
        assertTrue(body instanceof Map);
        return (Map<String, Object>) body;
    }
}


