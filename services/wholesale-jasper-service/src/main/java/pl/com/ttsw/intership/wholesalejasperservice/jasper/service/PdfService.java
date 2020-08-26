package pl.com.ttsw.intership.wholesalejasperservice.jasper.service;


import java.io.IOException;

public interface PdfService {

     void saveInvoicePdfToDb(String path, Long invoiceId) throws IOException;
     void saveOrderPdfToDb(String path, Long orderId) throws  IOException;
}
