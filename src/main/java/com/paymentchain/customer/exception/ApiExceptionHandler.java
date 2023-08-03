package com.paymentchain.customer.exception;

import com.paymentchain.customer.common.StandarizeApiExeceptionResponse;
import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Indico que esta clase es la axiliar de controller
public class ApiExceptionHandler {

    private static final String CODIGO_ERROR = "001";
    private static final String CODIGO_ERROR_VALIDATION = "002";
    private static final String ERROR_VALIDACION = "Error de validación";
    private static final String ERROR_VALIDACION_NO_EXISTE = "Error de validación, cliente no existe";

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<StandarizeApiExeceptionResponse> handleUnknownHostException(UnknownHostException ex) {
        StandarizeApiExeceptionResponse response = new StandarizeApiExeceptionResponse("Error de conexion","erorr-1024",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(BussinesRuleException.class)
    public ResponseEntity<StandarizeApiExeceptionResponse> handleBussinesRuleException(BussinesRuleException ex) {
        StandarizeApiExeceptionResponse response = new StandarizeApiExeceptionResponse("Error de conexion",ex.getCode(),ex.getMessage());
        return new ResponseEntity(response, ex.getHttpStatus());
    }
    
    

}
