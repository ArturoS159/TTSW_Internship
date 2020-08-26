package pl.com.ttsw.intership.wholesaleserverapp.kafka.exception.pdf.response;


public class PdfResponse {

    private String pdfResponse;

    public PdfResponse(String pdfResponse) {
        this.pdfResponse = pdfResponse;
    }

    public String getPdfResponse() {
        return pdfResponse;
    }

    public void setPdfResponse(String pdfResponse) {
        this.pdfResponse = pdfResponse;
    }
}
