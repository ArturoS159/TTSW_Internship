package pl.com.ttsw.intership.wholesaleserverapp.kafka.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.KafkaService;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.PdfService;

import java.security.Principal;

@RestController
@RequestMapping("/rest-service/pdf")
@CrossOrigin("*")
@AllArgsConstructor
public class PdfController {


    private final KafkaService kafkaService;
    private final PdfService pdfService;


    /**
     * Controller is responsible for returning pdf with invoice from database
     * @param nr order number
     * @return pdf in byte format
     * @throws InterruptedException
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> invoicePdf(@RequestParam(value="nr", required=true) Long nr, Principal principal) throws InterruptedException {
        return new ResponseEntity<>(pdfService.decodeInvoice(nr, principal), HttpStatus.OK);
    }

    /**
     * Controller is responsible for returning pdf with ordered products from database
     * @param nr order number
     * @return pdf in byte format
     * @throws InterruptedException
     */
    @PreAuthorize("hasRole('WAREHOUSE_OWNER') or hasRole('WORKER')")
    @GetMapping(value = "/order", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> orderPdf(@RequestParam(value="nr", required=true) Long nr,
                                           Principal principal) throws InterruptedException {
        return new ResponseEntity<>(pdfService.decodeOrder(nr, principal), HttpStatus.OK);
    }
}
