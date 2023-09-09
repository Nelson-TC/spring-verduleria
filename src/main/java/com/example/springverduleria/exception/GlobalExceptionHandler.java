package com.example.springverduleria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Environment environment;

    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        String activeProfile = environment.getActiveProfiles()[0];

        if ("prod".equals(activeProfile)) {
            /* Hide technical details on prod profile */
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error en el servidor en el perfil :(");
        } else {
            /* Return custom message with details on dev profile */
            String timestamp = LocalDateTime.now().toString();
            int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            String message = "Ocurrió un error en el servidor :(";
            String path = "";

            ErrorResponse errorResponse = new ErrorResponse(timestamp, status, error, message, path);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
