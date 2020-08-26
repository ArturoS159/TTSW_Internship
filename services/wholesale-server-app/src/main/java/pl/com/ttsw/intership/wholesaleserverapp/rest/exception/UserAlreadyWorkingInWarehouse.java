package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class UserAlreadyWorkingInWarehouse extends RuntimeException {
    public UserAlreadyWorkingInWarehouse() {
        super("User already working in warehouse");
    }
}
