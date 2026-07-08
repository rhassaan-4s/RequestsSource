package com._4s_.clients.web.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com._4s_.clients.web.exception.TenantNotFoundException;
import com._4s_.clients.web.exception.UserNotFoundException;
import com._4s_.clients.web.util.Tenant;

@ControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger LOGGER = Logger.getLogger(GlobalControllerAdvice.class.getName());

    @ModelAttribute("tenant")
    public String populateTenantName(@Tenant String tenant) {
        return tenant;
    }

    @ExceptionHandler(value = TenantNotFoundException.class)
    public String handleTenantNotFoundException(TenantNotFoundException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
        // TODO: redirect to error page or just a meaningful status code...
        return "redirect:/";
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
        // TODO: redirect to error page or just a meaningful status code...
        return "redirect:/";
    }
}
