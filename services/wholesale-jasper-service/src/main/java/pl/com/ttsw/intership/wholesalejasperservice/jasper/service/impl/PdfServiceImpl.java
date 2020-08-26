package pl.com.ttsw.intership.wholesalejasperservice.jasper.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.repository.PdfInvoiceRepository;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.repository.PdfOrderRepository;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.service.PdfService;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.utils.pdf.InvoicePdf;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.utils.pdf.OrderPdf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {

    private PdfInvoiceRepository invoiceRepository;
    private PdfOrderRepository orderRepository;

    public PdfServiceImpl(PdfInvoiceRepository invoiceRepository, PdfOrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
    }

    public void saveInvoicePdfToDb(String path, Long invoiceId) throws IOException {
        byte[] byteFile = Files.readAllBytes(Paths.get(path));
        InvoicePdf o = new InvoicePdf(byteFile, invoiceId);
        invoiceRepository.save(o);
    }

    public void saveOrderPdfToDb(String path, Long orderId) throws  IOException{
        byte[] byteFile = Files.readAllBytes(Paths.get(path));
        OrderPdf o = new OrderPdf(byteFile, orderId);
        orderRepository.save(o);
    }
}
