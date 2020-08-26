package pl.com.ttsw.intership.wholesaleserverapp.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.UserService;

@RestController
@RequestMapping("/rest-service/user")
@CrossOrigin("*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("#userId == principal.id || hasRole('WAREHOUSE_OWNER') || hasRole('WORKER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.CREATED);
    }
}
