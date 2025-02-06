package buildingblocks.problemdetails;

import buildingblocks.core.exception.BadRequestException;
import buildingblocks.core.exception.ConflictException;
import buildingblocks.core.exception.NotFoundException;
import buildingblocks.core.exception.ValidationException;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.KeyValuePair;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class CustomProblemDetailsHandler extends ResponseEntityExceptionHandler {

  private final Environment environment;
  private final Logger logger = LoggerFactory.getLogger(CustomProblemDetailsHandler.class);

  public CustomProblemDetailsHandler(Environment environment) {
    this.environment = environment;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> handleAllExceptions(
    Exception ex,
    WebRequest request
  ) {
    record ExceptionDetails(String detail, String title, HttpStatus status) {
    }

    ExceptionDetails details = switch (ex) {
      case ConflictException conflictEx -> new ExceptionDetails(
        conflictEx.getMessage(),
        conflictEx.getClass().getSimpleName(),
        HttpStatus.CONFLICT
      );

      case ValidationException validationEx -> new ExceptionDetails(
        validationEx.getMessage(),
        validationEx.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST
      );

      case BadRequestException badRequestEx -> new ExceptionDetails(
        badRequestEx.getMessage(),
        badRequestEx.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST
      );

      case NotFoundException notFoundEx -> new ExceptionDetails(
        notFoundEx.getMessage(),
        notFoundEx.getClass().getSimpleName(),
        HttpStatus.NOT_FOUND
      );

      case OptimisticLockException concurrencyEx -> new ExceptionDetails(
        concurrencyEx.getMessage(),
        concurrencyEx.getClass().getSimpleName(),
        HttpStatus.CONFLICT
      );

      case io.grpc.StatusRuntimeException grpcEx -> new ExceptionDetails(
        grpcEx.getMessage(),
        "GrpcException",
        HttpStatus.BAD_REQUEST
      );

      default -> new ExceptionDetails(
        ex.getMessage(),
        ex.getClass().getSimpleName(),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    };

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      details.status(),
      details.detail()
    );

    problemDetail.setType(URI.create("https://problems/" + details.title().toLowerCase()));
    problemDetail.setTitle(details.title());
    problemDetail.setProperty("timestamp", Instant.now());

    // Add stack trace in development environment
    if (isDevelopment()) {
      problemDetail.setProperty("exception", ex.toString());
    }

    // Log structured error details
    logger.atError().addKeyValue("details", JsonConverterUtils.serializeObject(details)).log("An error occurred while processing the request.");

    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  private boolean isDevelopment() {
    String[] activeProfiles = environment.getActiveProfiles();
    return activeProfiles.length > 0 && ("dev".equalsIgnoreCase(activeProfiles[0]) || "development".equalsIgnoreCase(activeProfiles[0]));
  }
}
