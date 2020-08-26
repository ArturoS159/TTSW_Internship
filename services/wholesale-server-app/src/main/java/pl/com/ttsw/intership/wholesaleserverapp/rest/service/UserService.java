package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;

public interface UserService {
    UserDto getUser(Long userId);
}
