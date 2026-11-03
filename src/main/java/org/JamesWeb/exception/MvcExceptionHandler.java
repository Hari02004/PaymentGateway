package org.JamesWeb.exception;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {
    @ExceptionHandler(LoginEmailException.class)
    public String loginEmailNotFoundException(LoginEmailException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "register";
    }
    @ExceptionHandler(ApiUserEmailNotFoundException.class)
    public String apiUserEmailNotFoundException(ApiUserEmailNotFoundException ex,Model model){
        model.addAttribute("error",ex.getMessage());
        return "UserMerchantNotAvailable";
    }
    @ExceptionHandler(MerchantAlreadyExistException.class)
    public String merchantEmailException(MerchantAlreadyExistException ex,Model model){
        model.addAttribute("error",ex.getMessage());
        return "merchantdetails";
    }
    @ExceptionHandler(OrderIdExpired.class)
    public String orderIdExpiredException(OrderIdExpired ex, Model model){
        model.addAttribute("error",ex.getMessage());
        return "Error";
    }
    @ExceptionHandler(LoginCredentialException.class)
    public String loginCredentialsException(LoginCredentialException ex,Model model){
        model.addAttribute("error",ex.getMessage());
        return "login";
    }
    @ExceptionHandler(DuplicateValueException.class)
    public String duplicateKeyException(DuplicateValueException ex,Model model){
        model.addAttribute("error",ex.getMessage());
        return "merchantdetails";
    }

}
