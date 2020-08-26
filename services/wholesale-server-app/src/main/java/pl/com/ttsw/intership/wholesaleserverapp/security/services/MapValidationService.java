package pl.com.ttsw.intership.wholesaleserverapp.security.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationService {

     ResponseEntity<?> MapValidationService(BindingResult result);
}
