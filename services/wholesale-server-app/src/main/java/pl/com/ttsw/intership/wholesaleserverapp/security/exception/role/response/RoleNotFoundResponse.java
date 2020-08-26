package pl.com.ttsw.intership.wholesaleserverapp.security.exception.role.response;

public class RoleNotFoundResponse {
    private String roleResponse;

    public RoleNotFoundResponse(String roleResponse) {
        this.roleResponse = roleResponse;
    }

    public String getRoleResponse() {
        return roleResponse;
    }

    public void setRoleResponse(String roleResponse) {
        this.roleResponse = roleResponse;
    }
}
