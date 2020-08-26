package pl.com.ttsw.intership.wholesaleserverapp.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.pdf.InvoicePdf;

import java.util.Optional;

@Repository
public interface PdfInvoiceRepository extends JpaRepository<InvoicePdf, Long> {

    Optional<InvoicePdf> findByInvoiceId(Long invoiceId);
}
