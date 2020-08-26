package pl.com.ttsw.intership.wsdltransformservice.transform.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.product_model.Products;
import pl.com.ttsw.intership.soap_first_warehouse.WaresList;
import pl.com.ttsw.intership.wsdltransformservice.transform.jaxb.JaxbHelper;
import pl.com.ttsw.intership.wsdltransformservice.transform.repository.ProductsRepository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Service
public class FirstWarehouseProductService implements ProductService {

    private final ProductsRepository productsRepository;

    public FirstWarehouseProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void transformToProductsModelAndSaveToDatabase(Object object) throws JAXBException, IOException, TransformerException {
        Products products = transformToFinalModel((WaresList) object);
        productsRepository.save(products);
    }

    public Products transformToFinalModel(WaresList waresList) throws JAXBException, IOException, TransformerException {
        String waresListXml = JaxbHelper.marshall(waresList, WaresList.class);
        Resource xsltFile = new ClassPathResource("xslt\\first-warehouse.xslt");
        String xmlAfterTransform = JaxbHelper.doXslTransform(waresListXml, xsltFile.getInputStream());
        return (Products) JaxbHelper.unmarshal(xmlAfterTransform, Products.class);
    }
}
