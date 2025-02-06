package buildingblocks.keycloak;

import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final Logger logger;

  public CustomAuthenticationEntryPoint(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException {

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      getStatus(authException),
      authException.getMessage()
    );

    problemDetail.setTitle(authException.getClass().getSimpleName());
    problemDetail.setDetail(authException.getMessage());
    problemDetail.setType(URI.create("https://problems/" + authException.getClass().getSimpleName().toLowerCase()));
    problemDetail.setProperty("timestamp", Instant.now().toString());
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    response.setStatus(getStatus(authException).value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(JsonConverterUtils.serializeObject(problemDetail));

    // Log structured error details
    logger.atError().addKeyValue("details", JsonConverterUtils.serializeObject(problemDetail)).log("An error occurred while processing the request.");
  }

  private HttpStatus getStatus(AuthenticationException exception) {
    return switch (exception) {
      case BadCredentialsException e -> HttpStatus.UNAUTHORIZED;
      case InsufficientAuthenticationException e -> HttpStatus.FORBIDDEN;
      default -> HttpStatus.UNAUTHORIZED;
    };
  }
}
