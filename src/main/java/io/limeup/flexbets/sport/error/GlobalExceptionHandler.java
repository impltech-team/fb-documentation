package io.limeup.flexbets.sport.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<Object> handleNotFound(WebClientResponseException ex) {
        log.warn("404 Not Found: {}", ex.getRequest().getURI());

        Map<String, Object> body = new HashMap<>();
        body.put("status", 404);
        body.put("error", "Not Found");
        body.put("message", "The requested resource could not be found.");
        body.put("path", ex.getRequest().getURI().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponse(WebClientResponseException ex) {
        log.error("WebClient error: {} {}", ex.getStatusCode(), ex.getStatusText());

        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode());
        body.put("error", ex.getStatusText());
        body.put("message", ex.getResponseBodyAsString());
        body.put("path", ex.getRequest().getURI().toString());

        return ResponseEntity.status(ex.getRawStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        log.error("Unhandled exception: ", ex);

        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
