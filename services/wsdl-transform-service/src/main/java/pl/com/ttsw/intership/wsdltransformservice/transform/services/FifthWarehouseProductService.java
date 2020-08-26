package pl.com.ttsw.intership.wsdltransformservice.transform.services;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.product_model.Products;
import pl.com.ttsw.intership.soap_fifth_warehouse.ProductsWarehouseList;
import pl.com.ttsw.intership.wsdltransformservice.transform.jaxb.JaxbHelper;
import pl.com.ttsw.intership.wsdltransformservice.transform.repository.ProductsRepository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Service
public class FifthWarehouseProductService implements ProductService {

    private final ProductsRepository productsRepository;

    public FifthWarehouseProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void transformToProductsModelAndSaveToDatabase(Object object) throws JAXBException, IOException, TransformerException {
        Products products = transformToFinalModel((ProductsWarehouseList) object);
        productsRepository.save(products);
    }

    public Products transformToFinalModel(ProductsWarehouseList productsList) throws JAXBException, IOException, TransformerException {
        String productListXml = JaxbHelper.marshall(productsList, ProductsWarehouseList.class);
        Resource xsltFile = new ClassPathResource("xslt\\fifth-warehouse.xslt");
        String xmlAfterTransform = JaxbHelper.doXslTransform(productListXml, xsltFile.getInputStream());
        return (Products) JaxbHelper.unmarshal(xmlAfterTransform, Products.class);
    }
}
