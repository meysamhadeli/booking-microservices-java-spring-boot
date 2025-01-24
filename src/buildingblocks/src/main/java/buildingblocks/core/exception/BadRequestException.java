package buildingblocks.core.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(String message, Integer code) {
        super(message, HttpStatus.BAD_REQUEST, code);
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
