package buildingblocks.core.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    public ConflictException(String message, Integer code) {
        super(message, HttpStatus.CONFLICT, code);
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
