package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException() {
        super("Warehouse not found");
    }
}
