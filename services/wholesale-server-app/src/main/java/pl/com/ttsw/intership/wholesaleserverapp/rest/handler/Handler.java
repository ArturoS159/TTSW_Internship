package pl.com.ttsw.intership.wholesaleserverapp.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.ApiError;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
@RestController
public class Handler {

    @ExceptionHandler(value = {UserNotFoundException.class, OrderNotFoundException.class, BasketItemNotFoundException.class, WarehouseNotFoundException.class, ProductNotFoundException.class, NoProductInDatabaseException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException err) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError("NOT_FOUND", err.getMessage(), httpStatus.value());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(value = {UserAlreadyWorkingInWarehouse.class})
    public ResponseEntity<ApiError> handleAlreadyWorking(RuntimeException err) {
        final HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError("USER_ALREADY_WORKING", err.getMessage(), httpStatus.value());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleValidationError(ConstraintViolationException ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }
        ApiError apiError = new ApiError("VALIDATION_ERROR", message.toString(), httpStatus.value());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}