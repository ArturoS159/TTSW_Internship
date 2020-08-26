package pl.com.ttsw.intership.wholesaleserverapp.security.services;

import pl.com.ttsw.intership.wholesaleserverapp.security.payload.ApiResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.JwtAuthenticationResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.LoginRequest;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.RegisterRequest;

public interface AuthService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
    ApiResponse register(RegisterRequest registerRequest);
}
