package pl.com.ttsw.intership.wholesaleserverapp.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.pdf.OrderPdf;

import java.util.Optional;

@Repository
public interface PdfOrderRepository extends JpaRepository<OrderPdf, Long> {

    Optional<OrderPdf> findByOrderNr(Long orderNr);
}
