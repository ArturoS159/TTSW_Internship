package pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response;

public class EmailNotFoundResponse {
    private String emailNotFoundException;

    public EmailNotFoundResponse(String emailNotFoundException) {
        this.emailNotFoundException = emailNotFoundException;
    }

    public String getEmailNotFoundException() {
        return emailNotFoundException;
    }

    public void setEmailNotFoundException(String emailNotFoundException) {
        this.emailNotFoundException = emailNotFoundException;
    }
}
