package dev.mohdsummers.dallasmuslimhub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyExistsException extends RuntimeException {
    private final String resourceName;

    public ResourceAlreadyExistsException(String resourceName) {
        super(String.format("%s already exists", resourceName));
        this.resourceName = resourceName;
    }
}
