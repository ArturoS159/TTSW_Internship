package pl.com.ttsw.intership.wholesalejasperservice.jasper.service;

import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList.OrderList;

public interface JasperReportService {
     void saveInvoiceAsPdf(Invoice invoice);
    void saveOrderAsPdf(OrderList orderList);

}
