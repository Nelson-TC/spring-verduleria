package com.example.springverduleria.exception;

public class ErrorResponse {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(String timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Agrega getters para todas las propiedades

    // Puedes agregar más propiedades según tus necesidades
}

