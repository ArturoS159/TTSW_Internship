package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private String street;
    private String postCode;
    private String city;
    private String nip;
    private String phoneNumber;
}
