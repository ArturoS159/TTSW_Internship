package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
