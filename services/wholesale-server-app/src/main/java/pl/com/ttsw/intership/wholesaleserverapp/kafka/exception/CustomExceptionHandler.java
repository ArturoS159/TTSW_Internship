package pl.com.ttsw.intership.wholesaleserverapp.kafka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.exception.pdf.PdfException;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.exception.pdf.response.PdfResponse;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handlePdfException(PdfException ex, WebRequest request) {
        PdfResponse exceptionResponse = new PdfResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
