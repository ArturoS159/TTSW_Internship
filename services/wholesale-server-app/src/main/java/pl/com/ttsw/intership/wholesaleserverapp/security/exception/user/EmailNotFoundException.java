package pl.com.ttsw.intership.wholesaleserverapp.security.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message){super(message);}
}
