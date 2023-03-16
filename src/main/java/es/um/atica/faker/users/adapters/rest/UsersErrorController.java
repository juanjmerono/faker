package es.um.atica.faker.users.adapters.rest;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.um.atica.faker.users.adapters.rest.dto.ErrorDTO;

@ControllerAdvice
public class UsersErrorController {
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNoSuchElement(Exception ex, HttpServletRequest req) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorDTO.builder()
                    .status(404)
                    .timestamp(System.currentTimeMillis())
                    .error(ex.getMessage())
                    .exception(ex.getClass().getName())
                    .path(req.getRequestURI())
                    .method(req.getMethod())
                    .principal(req.getUserPrincipal().getName())
                    .build());
    }

}
