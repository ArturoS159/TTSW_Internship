package pl.com.ttsw.intership.wholesalejasperservice.jasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.utils.pdf.OrderPdf;

@Repository
public interface PdfOrderRepository extends JpaRepository<OrderPdf, Long> {
}
