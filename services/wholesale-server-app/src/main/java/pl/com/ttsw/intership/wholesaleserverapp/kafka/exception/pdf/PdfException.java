package pl.com.ttsw.intership.wholesaleserverapp.kafka.exception.pdf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PdfException extends RuntimeException{

    public PdfException(String message) {
        super(message);
    }
}
