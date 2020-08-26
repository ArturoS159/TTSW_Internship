package pl.com.ttsw.intership.soapclient;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.com.ttsw.intership.soapclient.client.connector.SOAPConnector;
import pl.com.ttsw.intership.soapclient.client.entity.XmlFile;
import pl.com.ttsw.intership.soapclient.client.repository.postgres.XmlFileRepositoryPostgres;
import pl.com.ttsw.intership.waressoapservice.models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.time.LocalDate;


@Component
public class TestClass {

    private final SOAPConnector soapConnector;
    private final XmlFileRepositoryPostgres XmlFileRepositoryPostgres;

    public TestClass(SOAPConnector soapConnector, XmlFileRepositoryPostgres XmlFileRepositoryPostgres) {
        this.soapConnector = soapConnector;
        this.XmlFileRepositoryPostgres = XmlFileRepositoryPostgres;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws JAXBException {
        final String URL = "http://localhost:8080/soap-service";

        GetWareById wareRequest = new GetWareById();
        wareRequest.setId(3L);
        GetWareByIdResponse getWareByIdResponse =
                (GetWareByIdResponse) soapConnector.callWebService(URL, wareRequest);
        System.out.println(getWareByIdResponse.getWare());

        GetAllWares allWaresRequest = new GetAllWares();
        GetAllWaresResponse getAllWaresResponse =
                (GetAllWaresResponse) soapConnector.callWebService(URL, allWaresRequest);
        System.out.println(getAllWaresResponse);

        GetStuffByCode stuffRequest = new GetStuffByCode();
        stuffRequest.setCode(2L);
        GetStuffByCodeResponse getStuffByCodeResponse =
                (GetStuffByCodeResponse) soapConnector.callWebService(URL, stuffRequest);
        System.out.println(getStuffByCodeResponse);

        GetAllStuffs allStuffsRequest = new GetAllStuffs();
        GetAllStuffsResponse getAllStuffsResponse =
                (GetAllStuffsResponse) soapConnector.callWebService(URL, allStuffsRequest);
        System.out.println(getAllStuffsResponse);

        StringWriter stringWriter = new StringWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(GetWareByIdResponse.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        JAXBElement jaxbElement =
                new JAXBElement( new QName("", "getWareByIdResponse"),
                        GetWareByIdResponse.class,
                        getWareByIdResponse);
        jaxbMarshaller.marshal(jaxbElement, stringWriter);

        XmlFile xmlFile = new XmlFile();
        xmlFile.setFile(stringWriter.toString());
        xmlFile.setDate(LocalDate.now());
        XmlFileRepositoryPostgres.save(xmlFile);

        stringWriter = new StringWriter();
        jaxbContext = JAXBContext.newInstance(GetAllWaresResponse.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbElement =
                new JAXBElement( new QName("", "getAllWaresResponse"),
                        GetAllWaresResponse.class,
                        getAllWaresResponse);
        jaxbMarshaller.marshal(jaxbElement, stringWriter);

        xmlFile = new XmlFile();
        xmlFile.setFile(stringWriter.toString());
        xmlFile.setDate(LocalDate.now());
        XmlFileRepositoryPostgres.save(xmlFile);

        stringWriter = new StringWriter();
        jaxbContext = JAXBContext.newInstance(GetStuffByCodeResponse.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbElement =
                new JAXBElement( new QName("", "getStuffByCodeResponse"),
                        GetStuffByCodeResponse.class,
                        getStuffByCodeResponse);
        jaxbMarshaller.marshal(jaxbElement, stringWriter);

        xmlFile = new XmlFile();
        xmlFile.setFile(stringWriter.toString());
        xmlFile.setDate(LocalDate.now());
        XmlFileRepositoryPostgres.save(xmlFile);

        stringWriter = new StringWriter();
        jaxbContext = JAXBContext.newInstance(GetAllStuffsResponse.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbElement =
                new JAXBElement( new QName("", "getAllStuffsResponse"),
                        GetAllStuffsResponse.class,
                        getAllStuffsResponse);
        jaxbMarshaller.marshal(jaxbElement, stringWriter);

        xmlFile = new XmlFile();
        xmlFile.setFile(stringWriter.toString());
        xmlFile.setDate(LocalDate.now());
        XmlFileRepositoryPostgres.save(xmlFile);

    }
}
