package pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.pdf;

import javax.persistence.*;

@Entity(name = "orderpdf")
public class OrderPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdf_id")
    private Long pdfId;

    private byte[] pdf;

    @Column(name = "order_nr")
    private Long orderNr;

    public OrderPdf() {
    }

    public OrderPdf(byte[] pdf, Long orderNr) {
        this.pdf = pdf;
        this.orderNr = orderNr;
    }

    public Long getPdfId() {
        return pdfId;
    }

    public void setPdfId(Long pdfId) {
        this.pdfId = pdfId;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }
}
