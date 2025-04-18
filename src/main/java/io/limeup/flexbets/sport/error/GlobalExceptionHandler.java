package io.limeup.flexbets.sport.error;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Hidden
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<Object> handleNotFound(WebClientResponseException ex) {
        log.warn("404 Not Found: {}", ex.getRequest().getURI());

        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, 404);
        body.put(ConstantUtils.Common.ERROR, "Not Found");
        body.put(ConstantUtils.Common.MESSAGE, "The requested resource could not be found.");
        body.put(ConstantUtils.Common.PATH, ex.getRequest().getURI().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponse(WebClientResponseException ex) {
        log.error("WebClient error: {} {}", ex.getStatusCode(), ex.getStatusText());

        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, ex.getStatusCode());
        body.put(ConstantUtils.Common.ERROR, ex.getStatusText());
        body.put(ConstantUtils.Common.MESSAGE, ex.getResponseBodyAsString());
        body.put(ConstantUtils.Common.PATH, ex.getRequest().getURI().toString());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        log.error("Unhandled exception: ", ex);

        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, 500);
        body.put(ConstantUtils.Common.ERROR, "Internal Server Error");
        body.put(ConstantUtils.Common.MESSAGE, ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(value = HttpStatusCodeException.class)
    public ResponseEntity<Object> handleException(HttpStatusCodeException ex) {
        log.error("HttpStatusCodeException error: {} {}", ex.getStatusCode(), ex.getStatusText());

        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, ex.getStatusCode());
        body.put(ConstantUtils.Common.ERROR, ex.getStatusText());
        body.put(ConstantUtils.Common.MESSAGE, ex.getResponseBodyAsString());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("TypeMismatch error: {}", ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ConstantUtils.Common.ERROR, "Bad Request");
        ex.getRequiredType();
        body.put(ConstantUtils.Common.MESSAGE, String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName()));

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ConstantUtils.Common.ERROR, "Validation Failed");
        body.put(ConstantUtils.Common.MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(ConstantUtils.Common.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ConstantUtils.Common.ERROR, String.format("Missing required parameter: %s", ex.getParameterName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
