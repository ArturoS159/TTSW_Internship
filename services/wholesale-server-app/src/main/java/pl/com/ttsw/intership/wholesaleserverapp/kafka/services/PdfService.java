package pl.com.ttsw.intership.wholesaleserverapp.kafka.services;

import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

import java.security.Principal;
import java.util.List;

public interface PdfService {

    byte[] decodeInvoice(Long invoiceNr, Principal principal) throws InterruptedException;

    byte[] decodeOrder(Long orderNr, Principal principal) throws InterruptedException;

    void generatePdfFromOrder(List<Order> orders, User user);

}
