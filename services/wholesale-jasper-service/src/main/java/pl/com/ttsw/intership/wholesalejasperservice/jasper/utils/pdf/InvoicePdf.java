package pl.com.ttsw.intership.wholesalejasperservice.jasper.utils.pdf;

import javax.persistence.*;

@Entity(name = "invoicepdf")
public class InvoicePdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdf_id")
    private Long pdfId;



    private byte[] pdf;

    @Column(name = "invoice_nr")
    private Long invoiceId;

    public InvoicePdf() {
    }

    public InvoicePdf(byte[] pdf, Long invoiceId) {
        this.pdf = pdf;
        this.invoiceId = invoiceId;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
}
