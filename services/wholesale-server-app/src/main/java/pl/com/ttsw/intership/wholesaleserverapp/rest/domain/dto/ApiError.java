package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {
    private String error;
    private String message;
    private Integer status;
    private String timestamp;

    public ApiError(String error, String message, Integer status) {
        super();
        this.error = error;
        this.message = message;
        this.status = status;
        timestamp = LocalDateTime.now().toString();
    }
}