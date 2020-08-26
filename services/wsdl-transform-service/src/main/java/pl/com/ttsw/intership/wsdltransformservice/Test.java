package pl.com.ttsw.intership.wsdltransformservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.com.ttsw.intership.soap_fifth_warehouse.FifthWarehouseEndpointService;
import pl.com.ttsw.intership.soap_fifth_warehouse.ProductsWarehouseList;
import pl.com.ttsw.intership.soap_first_warehouse.FirstWarehouseEndpointService;
import pl.com.ttsw.intership.soap_first_warehouse.WaresList;
import pl.com.ttsw.intership.soap_fourth_warehouse.FourthWarehouseEndpointService;
import pl.com.ttsw.intership.soap_fourth_warehouse.Products;
import pl.com.ttsw.intership.soap_second_warehouse.ProductsList;
import pl.com.ttsw.intership.soap_second_warehouse.SecondWarehouseEndpointService;
import pl.com.ttsw.intership.soap_third_warehouse.ProductsFromWarehouse;
import pl.com.ttsw.intership.soap_third_warehouse.ThirdWarehouseEndpointService;
import pl.com.ttsw.intership.wsdltransformservice.transform.jaxb.JaxbHelper;
import pl.com.ttsw.intership.wsdltransformservice.transform.repository.ProductsRepository;
import pl.com.ttsw.intership.wsdltransformservice.transform.services.ProductService;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@Component
public class Test {

    FirstWarehouseEndpointService firstWarehouseEndpointService = new FirstWarehouseEndpointService();
    SecondWarehouseEndpointService secondWarehouseEndpointService = new SecondWarehouseEndpointService();
    ThirdWarehouseEndpointService thirdWarehouseEndpointService = new ThirdWarehouseEndpointService();
    FourthWarehouseEndpointService fourthWarehouseEndpointService = new FourthWarehouseEndpointService();
    FifthWarehouseEndpointService fifthWarehouseEndpointService = new FifthWarehouseEndpointService();

    @Autowired
    ProductsRepository productsRepository;

    private final ProductService firstWarehouseProductService;
    private final ProductService secondWarehouseProductService;
    private final ProductService thirdWarehouseProductService;
    private final ProductService fourthWarehouseProductService;
    private final ProductService fifthWarehouseProductService;

    public Test(@Qualifier("firstWarehouseProductService") ProductService firstWarehouseProductService,
                @Qualifier("secondWarehouseProductService") ProductService secondWarehouseProductService,
                @Qualifier("thirdWarehouseProductService") ProductService thirdWarehouseProductService,
                @Qualifier("fourthWarehouseProductService") ProductService fourthWarehouseProductService,
                @Qualifier("fifthWarehouseProductService") ProductService fifthWarehouseProductService) {
        this.firstWarehouseProductService = firstWarehouseProductService;
        this.secondWarehouseProductService = secondWarehouseProductService;
        this.thirdWarehouseProductService = thirdWarehouseProductService;
        this.fourthWarehouseProductService = fourthWarehouseProductService;
        this.fifthWarehouseProductService = fifthWarehouseProductService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void test() throws JAXBException, IOException, TransformerException {

        WaresList allWares = firstWarehouseEndpointService.getFirstWarehouseEndpointPort().getAllWares();
        firstWarehouseProductService.transformToProductsModelAndSaveToDatabase(allWares);

        ProductsList productsList = secondWarehouseEndpointService.getSecondWarehouseEndpointPort().getAllProductsList();
        secondWarehouseProductService.transformToProductsModelAndSaveToDatabase(productsList);

        ProductsFromWarehouse products = thirdWarehouseEndpointService.getThirdWarehouseEndpointPort().getAllProducts();
        thirdWarehouseProductService.transformToProductsModelAndSaveToDatabase(products);

        Products allProducts = fourthWarehouseEndpointService.getFourthWarehouseEndpointPort().getAllProducts();
        fourthWarehouseProductService.transformToProductsModelAndSaveToDatabase(allProducts);

        ProductsWarehouseList allProductsInWarehouse = fifthWarehouseEndpointService.getFifthWarehouseEndpointPort().getAllProductsInWarehouse();
        fifthWarehouseProductService.transformToProductsModelAndSaveToDatabase(allProductsInWarehouse);
    }
}