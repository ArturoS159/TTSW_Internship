package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class NoProductInDatabaseException extends RuntimeException {
    public NoProductInDatabaseException() {
        super("No products found in database");
    }
}
