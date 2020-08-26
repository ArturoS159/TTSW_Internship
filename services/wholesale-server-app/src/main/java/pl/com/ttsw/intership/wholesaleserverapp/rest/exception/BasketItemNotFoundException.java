package pl.com.ttsw.intership.wholesaleserverapp.rest.exception;

public class BasketItemNotFoundException extends RuntimeException {
    public BasketItemNotFoundException() {
        super("BasketItem not found");
    }
}
