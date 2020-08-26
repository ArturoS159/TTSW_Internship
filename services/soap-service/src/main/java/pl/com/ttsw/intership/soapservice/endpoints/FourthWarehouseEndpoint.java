package pl.com.ttsw.intership.soapservice.endpoints;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.com.ttsw.intership.soap_fourth_warehouse.GetAllProductsListResponse;
import pl.com.ttsw.intership.soap_fourth_warehouse.Products;
import pl.com.ttsw.intership.soapservice.services.FourthWarehouseService;

@Endpoint
public class FourthWarehouseEndpoint {

    private final FourthWarehouseService fourthWarehouseService;

    public FourthWarehouseEndpoint(FourthWarehouseService fourthWarehouseService) {
        this.fourthWarehouseService = fourthWarehouseService;
    }

    @PayloadRoot(namespace = "http://www.intership.ttsw.com.pl/soap-fourth-warehouse", localPart = "getAllProducts")
    @ResponsePayload
    public GetAllProductsListResponse getAllProducts() {
        Products products = new Products();
        products.getProduct().addAll(fourthWarehouseService.getAllProducts());
        GetAllProductsListResponse allResponse = new GetAllProductsListResponse();
        allResponse.setProducts(products);
        return allResponse;
    }

}

