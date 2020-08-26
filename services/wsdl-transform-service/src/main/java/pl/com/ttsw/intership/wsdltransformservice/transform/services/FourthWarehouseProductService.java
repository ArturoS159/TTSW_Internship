package pl.com.ttsw.intership.wsdltransformservice.transform.services;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.product_model.Products;
import pl.com.ttsw.intership.wsdltransformservice.transform.jaxb.JaxbHelper;
import pl.com.ttsw.intership.wsdltransformservice.transform.repository.ProductsRepository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Service
public class FourthWarehouseProductService implements ProductService {

    private final ProductsRepository productsRepository;

    public FourthWarehouseProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void transformToProductsModelAndSaveToDatabase(Object object) throws JAXBException, IOException, TransformerException {
        Products products = transformToFinalModel((pl.com.ttsw.intership.soap_fourth_warehouse.Products) object);
        productsRepository.save(products);
    }

    public Products transformToFinalModel(pl.com.ttsw.intership.soap_fourth_warehouse.Products productsList) throws JAXBException, IOException, TransformerException {
        String productListXml = JaxbHelper.marshall(productsList, pl.com.ttsw.intership.soap_fourth_warehouse.Products.class);
        Resource xsltFile = new ClassPathResource("xslt\\fourth-warehouse.xslt");
        String xmlAfterTransform = JaxbHelper.doXslTransform(productListXml, xsltFile.getInputStream());
        return (Products) JaxbHelper.unmarshal(xmlAfterTransform, Products.class);
    }
}
