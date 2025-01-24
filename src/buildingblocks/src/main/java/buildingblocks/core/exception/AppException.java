package buildingblocks.core.exception;

import org.springframework.http.HttpStatus;

public class AppException extends CustomException {

    public AppException(String message, Integer code) {
        super(message, HttpStatus.BAD_REQUEST, code);
    }

    public AppException() {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public AppException(String message, HttpStatus statusCode, Integer code) {
        super(message, statusCode, code);
    }

    public AppException(String message, Exception innerException, Integer code) {
        super(message, innerException, HttpStatus.BAD_REQUEST, code);
    }
}