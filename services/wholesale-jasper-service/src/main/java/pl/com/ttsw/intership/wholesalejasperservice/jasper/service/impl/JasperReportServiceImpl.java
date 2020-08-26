package pl.com.ttsw.intership.wholesalejasperservice.jasper.service.impl;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.service.JasperReportService;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.service.PdfService;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList.OrderList;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JasperReportServiceImpl implements JasperReportService {

    private PdfService pdfService;

    @Value("${app.jasper.path}")
    private String filePath;

    @Value("${app.jasper.date.format: yyyy-mm-dd_hh-mm-ss}")
    private String dateFormat;


    public JasperReportServiceImpl(PdfService pdfService) {
        this.pdfService = pdfService;
    }



    public void saveInvoiceAsPdf(Invoice invoice){
        try {
            Map<String, Object> parameters = mapInvoiceParameters(invoice);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            StringWriter path = createFileNameAndPath(dateTimeFormatter, "_invoice.pdf");


            saveAsPdf("invoice.jrxml",
                    path.toString(),
                    parameters,
                    invoice.getBasketItems());
            log.info("Invoice generated");

            pdfService.saveInvoicePdfToDb(path.toString(),invoice.getInvoiceNr());
            log.info("Invoice pdf saved into database");
        }catch (FileNotFoundException e){
            log.error("File error, Message: {}", e.getMessage());
        }
        catch (JRException e){
            log.error("jasper error, Message: {}", e.getMessage());
        }
        catch (IOException e) {
            log.error("Error saving invoice pdf to db Message: {}", e.getMessage());
        }catch (Exception e){
            log.error("Exception: {}", e.getMessage());
        }
    }



    public void saveOrderAsPdf(OrderList orderList){
        try {
            BigDecimal sum = orderList.getBasketItems().stream()
                    .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            //Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("totalCost", sum.toString());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            StringWriter path = createFileNameAndPath(dateTimeFormatter, "_order.pdf");


            saveAsPdf("order.jrxml",
                    path.toString(),
                    parameters,
                    orderList.getBasketItems());

            pdfService.saveOrderPdfToDb(path.toString(),orderList.getOrder_nr());
            log.info("Order pdf saved into database");
        }
        catch (FileNotFoundException e){
            log.error("File error, Message: {}", e.getMessage());
        }
        catch (JRException e){
            log.error("jasper error, Message: {}", e.getMessage());
        }
        catch (IOException e) {
            log.error("Error saving order pdf to db Message: {}", e.getMessage());
        }catch (Exception e){
            log.error("Exception: {}", e.getMessage());
        }
    }

    private Map<String, Object> mapInvoiceParameters(Invoice invoice) {
        BigDecimal sum = invoice.getBasketItems().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //Parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoiceNr", invoice.getInvoiceNr());

        parameters.put("dIssue", invoice.getDateIssue());
        parameters.put("dSell", invoice.getDateSell());
        parameters.put("dFinalize", invoice.getDateFinalize());

        parameters.put("wName", invoice.getWarehouseName());
        parameters.put("wCity", invoice.getWarehouseCity());
        parameters.put("wAddress", invoice.getWarehouseAddress());

        parameters.put("bName", invoice.getBuyerName());
        parameters.put("bAddress", invoice.getBuyerAddress());
        parameters.put("bPostCode", invoice.getBuyerPostCode());
        parameters.put("bCity", invoice.getBuyerCity());
        parameters.put("bNip", invoice.getBuyerNip());
        parameters.put("bPhone", invoice.getBuyerPhone());
        parameters.put("bEmail", invoice.getBuyerEmail());


        parameters.put("sName", invoice.getSellerName());
        parameters.put("sAddress", invoice.getSellerAddress());
        parameters.put("sPostCode", invoice.getSellerPostCode());
        parameters.put("sCity", invoice.getSellerCity());
        parameters.put("sNip", invoice.getSellerNip());
        parameters.put("sPhone", invoice.getSellerPhone());
        parameters.put("sEmail", invoice.getSellerEmail());

        parameters.put("totalCost", sum.toString());
        return parameters;
    }

    private StringWriter createFileNameAndPath(DateTimeFormatter dateTimeFormatter, String name) {
        StringWriter path = new StringWriter();
        path.append(filePath);
        path.append(dateTimeFormatter.format(LocalDateTime.now()));
        path.append(name);
        return path;
    }

    private void saveAsPdf(String jrxml,
                           String path,
                           Map<String, Object> parameters,
                           List<?> list) throws FileNotFoundException, JRException {

        //Load jrxml file
        log.info("file load");
        InputStream stream = getClass().getResourceAsStream("/static/"+jrxml);
        File file = new File(jrxml);
        log.info("report");
        JasperReport report = JasperCompileManager.compileReport(stream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,dataSource);
        log.info("exporting");
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                path);
    }

}
