package es.um.atica.faker.users.adapters.rest;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.um.atica.faker.users.adapters.rest.dto.ErrorDTO;

@ControllerAdvice
public class UsersErrorController {

    private ResponseEntity<ErrorDTO> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest req) {
        return ResponseEntity
            .status(status)
            .body(ErrorDTO.builder()
                    .status(status.value())
                    .timestamp(System.currentTimeMillis())
                    .error(ex.getMessage())
                    .exception(ex.getClass().getName())
                    .path(req.getRequestURI())
                    .method(req.getMethod())
                    .principal(req.getUserPrincipal().getName())
                    .build());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchElement(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, req);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorDTO> handleUnsupportedException(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex, req);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<ErrorDTO> handleAuthorizationServiceException(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex, req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex, HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, req);
    }

}
