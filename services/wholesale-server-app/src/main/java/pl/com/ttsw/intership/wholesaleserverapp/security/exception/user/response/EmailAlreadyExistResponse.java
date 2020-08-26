package pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response;

public class EmailAlreadyExistResponse {
    private String emailAlreadyExist;

    public EmailAlreadyExistResponse(String emailAlreadyExist) {
        this.emailAlreadyExist = emailAlreadyExist;
    }

    public String getEmailAlreadyExist() {

        return emailAlreadyExist;
    }

    public void setEmailAlreadyExist(String emailAlreadyExist) {
        this.emailAlreadyExist = emailAlreadyExist;
    }
}
