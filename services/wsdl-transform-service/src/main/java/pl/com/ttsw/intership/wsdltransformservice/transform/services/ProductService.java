package pl.com.ttsw.intership.wsdltransformservice.transform.services;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface ProductService {
    void transformToProductsModelAndSaveToDatabase(Object object) throws JAXBException, IOException, TransformerException;
}
