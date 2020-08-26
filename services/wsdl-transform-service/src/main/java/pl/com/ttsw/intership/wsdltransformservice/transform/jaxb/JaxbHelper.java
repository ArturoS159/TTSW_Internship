package pl.com.ttsw.intership.wsdltransformservice.transform.jaxb;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbHelper {

    public static String marshall(Object object, Class<?> classesToBeBound) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        Marshaller marshaller = JAXBContext.newInstance(classesToBeBound).createMarshaller();
        JAXBElement jaxbElement =
                new JAXBElement(new QName("", object.getClass().getSimpleName()),
                        classesToBeBound,
                        object);
        marshaller.marshal(jaxbElement, stringWriter);
        return stringWriter.toString();
    }

    public static Object unmarshal(String xml, Class<?> classesToBeBound) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller
                .unmarshal(new StreamSource(new ByteArrayInputStream(xml.getBytes())), classesToBeBound).getValue();
    }

    public static String doXslTransform(String xml, InputStream inputStream) throws TransformerException {
        StringWriter stringWriter = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(inputStream));
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(new StreamSource(new StringReader(xml)), result);
        return stringWriter.toString();
    }

}
