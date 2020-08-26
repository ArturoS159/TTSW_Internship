package pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.response;

public class UserDoesntHaveAccessResponse {
    private String userDoesntHaveAcces;

    public UserDoesntHaveAccessResponse(String userDoesntHaveAcces) {
        this.userDoesntHaveAcces = userDoesntHaveAcces;
    }

    public String getUserDoesntHaveAcces() {
        return userDoesntHaveAcces;
    }

    public void setUserDoesntHaveAcces(String userDoesntHaveAcces) {
        this.userDoesntHaveAcces = userDoesntHaveAcces;
    }
}
