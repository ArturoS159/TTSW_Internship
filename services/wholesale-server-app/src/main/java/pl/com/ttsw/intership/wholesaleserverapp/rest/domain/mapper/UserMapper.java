package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper;

import org.mapstruct.Mapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
