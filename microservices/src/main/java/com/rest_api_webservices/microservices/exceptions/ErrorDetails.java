package com.rest_api_webservices.microservices.exceptions;

import java.time.LocalDateTime;

/**
 * Represents the structure of error details returned in API responses.
 */
public class ErrorDetails {

    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }


}
