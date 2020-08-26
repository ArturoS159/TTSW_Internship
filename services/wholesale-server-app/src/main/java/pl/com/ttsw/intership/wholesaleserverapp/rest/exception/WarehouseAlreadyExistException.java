package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class WarehouseAlreadyExistException extends RuntimeException {
    public WarehouseAlreadyExistException() {
        super("Warehouse already exist");
    }
}
