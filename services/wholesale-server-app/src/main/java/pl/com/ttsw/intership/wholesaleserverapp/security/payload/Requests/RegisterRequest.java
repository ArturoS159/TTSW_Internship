package pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank(message = "Password must not be null")
    @Size(max = 120)
    private String password;

    @NotBlank(message = "Email must not be null")
    @Email
    private String email;

    @NotBlank(message = "Street must not be null")
    private String street;

    @NotBlank(message = "PostCode must not be null")
    @Column(name = "post_code")
    private String postCode;

    @NotBlank(message = "City must not be null")
    private String city;

    @NotBlank(message = "NIP must not be null")
    private String nip;

    @NotBlank(message = "PhoneNumber must not be null")
    private String phoneNumber;



}
