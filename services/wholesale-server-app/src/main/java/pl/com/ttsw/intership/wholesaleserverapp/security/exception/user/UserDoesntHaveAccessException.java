package pl.com.ttsw.intership.wholesaleserverapp.security.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserDoesntHaveAccessException extends RuntimeException{
    public UserDoesntHaveAccessException(String message){super(message);}
}
