package pl.com.ttsw.intership.wholesaleserverapp.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.role.RoleNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.role.response.RoleNotFoundResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.EmailAlreadyExistException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.EmailNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.UserDoesntHaveAccessException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.UserNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response.EmailAlreadyExistResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response.EmailNotFoundResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response.UserDoesntHaveAccessResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response.UserNotFoundResponse;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailAlreadyExistException(EmailAlreadyExistException ex, WebRequest request) {
        EmailAlreadyExistResponse exceptionResponse = new EmailAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailNotFundException(EmailNotFoundException ex, WebRequest request) {
        EmailNotFoundResponse exceptionResponse = new EmailNotFoundResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRoleException(RoleNotFoundException ex, WebRequest request) {
        RoleNotFoundResponse exceptionResponse = new RoleNotFoundResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        UserNotFoundResponse exceptionResponse = new UserNotFoundResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserDoesntHaveAccess(UserDoesntHaveAccessException ex, WebRequest request) {
        UserDoesntHaveAccessResponse exceptionResponse = new UserDoesntHaveAccessResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
    }
}
