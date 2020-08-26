package pl.com.ttsw.intership.wholesaleserverapp.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.ApiResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.JwtAuthenticationResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.LoginRequest;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.RegisterRequest;
import pl.com.ttsw.intership.wholesaleserverapp.security.services.AuthService;
import pl.com.ttsw.intership.wholesaleserverapp.security.services.MapValidationService;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/rest-service/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController{

    private final AuthService authService;
    private final MapValidationService mapValidationService;


    /**
     * Login controller
     * @param loginRequest request body
     * @param result error result map
     * @return json response with token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<JwtAuthenticationResponse>(authService.login(loginRequest), HttpStatus.OK);
    }

    /**
     * Register controller
     * @param registerRequest request body
     * @param result error result map
     * @return json response with message
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<ApiResponse>(authService.register(registerRequest), HttpStatus.OK);
    }
}
