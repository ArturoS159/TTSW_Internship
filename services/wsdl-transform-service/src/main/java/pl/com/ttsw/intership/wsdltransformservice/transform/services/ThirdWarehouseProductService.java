package pl.com.ttsw.intership.wsdltransformservice.transform.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.product_model.Products;
import pl.com.ttsw.intership.soap_third_warehouse.ProductsFromWarehouse;
import pl.com.ttsw.intership.wsdltransformservice.transform.jaxb.JaxbHelper;
import pl.com.ttsw.intership.wsdltransformservice.transform.repository.ProductsRepository;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Service
public class ThirdWarehouseProductService implements ProductService {

    private final ProductsRepository productsRepository;

    public ThirdWarehouseProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void transformToProductsModelAndSaveToDatabase(Object object) throws JAXBException, IOException, TransformerException {
        Products products = transformToFinalModel((ProductsFromWarehouse) object);
        productsRepository.save(products);
    }

    public Products transformToFinalModel(ProductsFromWarehouse productsList) throws JAXBException, IOException, TransformerException {
        String productListXml = JaxbHelper.marshall(productsList, ProductsFromWarehouse.class);
        Resource xsltFile = new ClassPathResource("xslt\\third-warehouse.xslt");
        String xmlAfterTransform = JaxbHelper.doXslTransform(productListXml, xsltFile.getInputStream());
        return (Products) JaxbHelper.unmarshal(xmlAfterTransform, Products.class);
    }
}
