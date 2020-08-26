package pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.UserMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.UserNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.UserService;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUser(Long userId) {
        return userMapper.toUserDto(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }
}
