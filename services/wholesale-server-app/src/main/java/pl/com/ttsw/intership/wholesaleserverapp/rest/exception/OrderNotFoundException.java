package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("Order not found");
    }
}
