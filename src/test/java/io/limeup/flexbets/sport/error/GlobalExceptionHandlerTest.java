package io.limeup.flexbets.sport.error;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundShouldReturn404() {
        URI uri = URI.create("https://example.com/test");
        WebClientResponseException ex = mock(WebClientResponseException.NotFound.class);
        HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getURI()).thenReturn(uri);
        when(ex.getRequest()).thenReturn(httpRequest);

        ResponseEntity<Object> response = handler.handleNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get(ConstantUtils.Common.STATUS)).isEqualTo(404);
        assertThat(body.get(ConstantUtils.Common.ERROR)).isEqualTo("Not Found");
        assertThat(body.get(ConstantUtils.Common.PATH)).isEqualTo("https://example.com/test");
    }


    @Test
    void handleWebClientResponseShouldReturnDetails() {
        URI uri = URI.create("https://example.com/test");
        WebClientResponseException ex = mock(WebClientResponseException.class);
        HttpRequest request = mock(HttpRequest.class);

        when(request.getURI()).thenReturn(uri);
        when(ex.getRequest()).thenReturn(request);
        when(ex.getStatusCode()).thenReturn(HttpStatus.BAD_GATEWAY);
        when(ex.getStatusText()).thenReturn("Bad Gateway");
        when(ex.getResponseBodyAsString()).thenReturn("backend error");

        ResponseEntity<Object> response = handler.handleWebClientResponse(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get(ConstantUtils.Common.ERROR)).isEqualTo("Bad Gateway");
        assertThat(body.get(ConstantUtils.Common.MESSAGE)).isEqualTo("backend error");
        assertThat(body.get(ConstantUtils.Common.PATH)).isEqualTo("https://example.com/test");
    }

    @Test
    void handleGenericShouldReturn500() {
        RuntimeException ex = new RuntimeException("Something went wrong");
        ResponseEntity<Object> response = handler.handleGeneric(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleHttpStatusCodeExceptionShouldReturnCodeAndBody() {
        HttpStatusCodeException ex = HttpClientErrorException.create(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                null,
                "no access".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<Object> response = handler.handleException(ex);
        Map<?, ?> body = (Map<?, ?>) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(body.get(ConstantUtils.Common.MESSAGE)).isEqualTo("no access");
    }

    @Test
    void handleTypeMismatch_ShouldReturnBadRequest() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", Integer.class, "limit", null, new IllegalArgumentException("invalid"));

        ResponseEntity<Object> response = handler.handleTypeMismatchException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleValidationException_ShouldReturn400() {
        ValidationException ex = new ValidationException("Invalid input");

        ResponseEntity<Object> response = handler.handleValidationException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMissingParameter_ShouldReturn400() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("marketId", "Integer");

        ResponseEntity<Object> response = handler.handleMissingServletRequestParameter(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleConstraintViolation_ShouldReturnErrors() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("fieldName");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be null");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

        ResponseEntity<?> response = handler.handleConstraintViolationException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    void handleMethodArgumentNotValid_ShouldReturnFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        var fieldError = mock(org.springframework.validation.FieldError.class);
        when(fieldError.getField()).thenReturn("sortBy");
        when(fieldError.getDefaultMessage()).thenReturn("must not be blank");

        var bindingResult = mock(org.springframework.validation.BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<?> response = handler.handleMethodArgumentNotValidException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
