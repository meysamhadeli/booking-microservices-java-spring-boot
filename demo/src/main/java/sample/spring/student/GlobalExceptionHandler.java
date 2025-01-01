//package sample.spring.student;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ProblemDetail;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.concurrent.CompletionException;
//
//    @ControllerAdvice
//    public class GlobalExceptionHandler {
//
//        @ExceptionHandler(IllegalArgumentException.class) // BadRequest
//        public ProblemDetail handleBadRequest(IllegalArgumentException ex, WebRequest request) {
//            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//            problemDetail.setTitle("Bad Request");
//            problemDetail.setInstance(request.getDescription(false));
//            return problemDetail;
//        }
//
//        @ExceptionHandler(SecurityException.class) // Unauthorized or Forbidden
//        public ProblemDetail handleSecurityException(SecurityException ex, WebRequest request) {
//            HttpStatus status = ex.getMessage().contains("Unauthorized") ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;
//            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
//            problemDetail.setTitle(status == HttpStatus.UNAUTHORIZED ? "Unauthorized" : "Forbidden");
//            problemDetail.setInstance(request.getDescription(false));
//            return problemDetail;
//        }
//
//        @ExceptionHandler(ResourceNotFoundException.class) // NotFound
//        public ProblemDetail handleNotFound(ResourceNotFoundException ex, WebRequest request) {
//            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
//            problemDetail.setTitle("Resource Not Found");
//            problemDetail.setInstance(request.getDescription(false));
//            return problemDetail;
//        }
//
//        @ExceptionHandler(CompletionException.class) // For async exceptions
//        public ProblemDetail handleAsyncException(CompletionException ex, WebRequest request) {
//            Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
//            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
//            problemDetail.setTitle("Internal Server Error");
//            problemDetail.setInstance(request.getDescription(false));
//            return problemDetail;
//        }
//
//        @ExceptionHandler(Exception.class) // General/InternalServerError
//        public ProblemDetail handleGeneralException(Exception ex, WebRequest request) {
//            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//            problemDetail.setTitle("Internal Server Error");
//            problemDetail.setInstance(request.getDescription(false));
//            return problemDetail;
//        }
//    }
//
//}