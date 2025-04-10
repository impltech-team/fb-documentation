package io.limeup.flexbets.sport.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class FlexBetsSportNotFoundException extends HttpStatusCodeException {

    public FlexBetsSportNotFoundException() {
        this("");
    }

    public FlexBetsSportNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}

