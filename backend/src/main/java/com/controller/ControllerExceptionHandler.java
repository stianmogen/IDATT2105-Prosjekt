package com.controller;

import com.exception.EmailInUseException;
import com.exception.EntityNotFoundException;
import com.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

      @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
      @ExceptionHandler(Exception.class)
      public Response handleUncaughtException(Exception exception){
            log.error("[X] Error caught while processing request {}", exception.getMessage());
            exception.printStackTrace();
            return new Response(exception.getMessage());
      }

      @ResponseStatus(HttpStatus.BAD_REQUEST)
      @ExceptionHandler(MethodArgumentNotValidException.class)
      public Response handleValidationExceptions(MethodArgumentNotValidException exception){
            Map<String, String> errorMessages = new HashMap<>();

            exception.getBindingResult().getFieldErrors().forEach(error -> {
                  errorMessages.put(error.getField(), error.getDefaultMessage());
            });

            String message = "One or more method arguments are invalid";

            log.error("[X] Error caught {}", message);
            return new Response(message, errorMessages);
      }

      @ResponseStatus(value = HttpStatus.NOT_FOUND)
      @ExceptionHandler({EntityNotFoundException.class})
      public Response handleEntityNotFound(EntityNotFoundException exception){
            log.error("[X] Error caught while processing request {}", exception.getMessage());
            return new Response(exception.getMessage());
      }


      @ResponseStatus(value = HttpStatus.FORBIDDEN)
      @ExceptionHandler(EmailInUseException.class)
      public Response handleEmailInUse(EmailInUseException exception){
            log.error("[X] Error caught while processing request {}", exception.getMessage());
            return new Response(exception.getMessage());
      }

}
