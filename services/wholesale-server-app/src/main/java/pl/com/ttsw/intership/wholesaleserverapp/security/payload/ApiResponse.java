package pl.com.ttsw.intership.wholesaleserverapp.security.payload;

public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean sucess, String message) {
        this.success = sucess;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean sucess) {
        this.success = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
