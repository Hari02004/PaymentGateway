package org.JamesWeb.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.DTO.ApiResponse;
import org.JamesWeb.DTO.ErrorDto;
import org.JamesWeb.mapper.ErrorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorMapper errorMapper;
    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserEmailException(UserEmailNotFoundException ex, HttpServletRequest request){
        ErrorDto dto = errorMapper.toDto(
                HttpStatus.NOT_FOUND.value(),
                "USER_EMAIL_NOT_FOUND",
                ex.getMessage() ,
                "Authentication-Service",
                request);
        ApiResponse<ErrorDto> apiResponse = convertToApiResponse(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthenticationPasswordException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAuthenticationException(AuthenticationPasswordException ex,HttpServletRequest request){
        ErrorDto dto = errorMapper.toDto(
                HttpStatus.FORBIDDEN.value(),
                "AUTHENTICATION_MISMATCH",
                ex.getMessage() ,
                "Authentication-Service",
                request);
        ApiResponse<ErrorDto> apiResponse = convertToApiResponse(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }
    /*@ExceptionHandler(OrderIdExpired.class)
    public ResponseEntity<ApiResponse<?>> OrderIdExpired(OrderIdExpired ex,HttpServletRequest request){
        ErrorDto error = new ErrorDto();
        error.setStatus(HttpStatus.GONE.value());
        error.setErrorCode("ORDER_ID_IS_INVALID");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());
        error.setServiceName("Merchant payment process");
        error.setTraceId((String)(request.getAttribute("traceId")));
        ErrorDto dto = errorMapper.toDto(
                HttpStatus.GONE.value(),
                "ORDER_ID_IS_INVALID",
                ex.getMessage() ,
                "Merchant payment process",
                request);
        ApiResponse<ErrorDto> apiResponse = convertToApiResponse(error);
        return new ResponseEntity<>(apiResponse, HttpStatus.GONE);
    }*/
    @ExceptionHandler(ApiExceptionHandler.class)
    public ResponseEntity<ApiResponse<?>> apiKeyNotFound(ApiExceptionHandler ex,HttpServletRequest request){
        ErrorDto dto = errorMapper.toDto(
                HttpStatus.NOT_FOUND.value(),
                "API_KEY_INVALID",
                ex.getMessage() ,
                "Merchant payment transaction process",
                request);
        ApiResponse<ErrorDto> apiResponse = convertToApiResponse(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdminErrorException.class)
    public ResponseEntity<ApiResponse<?>> adminError(AdminErrorException ex,HttpServletRequest request){
        ErrorDto dto = errorMapper.toDto(
                HttpStatus.NOT_FOUND.value(),
                "Admin login error",
                ex.getMessage() ,
                "Admin_Exception",
                request);
        ApiResponse<ErrorDto> apiResponse = convertToApiResponse(dto);
        return new ResponseEntity<>(apiResponse,HttpStatus.FORBIDDEN);
    }
    public ApiResponse<ErrorDto> convertToApiResponse(ErrorDto errorDto){
        return new ApiResponse<>(false,"Payment gateway fails",errorDto);
    }
}
