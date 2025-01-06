package buildingblocks.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(String message, Integer code) {
        super(message, HttpStatus.NOT_FOUND, code);
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}