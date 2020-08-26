package pl.com.ttsw.intership.soapservice.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.com.ttsw.intership.soap_first_warehouse.GetAllWaresResponse;
import pl.com.ttsw.intership.soap_first_warehouse.WaresList;
import pl.com.ttsw.intership.soap_second_warehouse.GetAllProductsListResponse;
import pl.com.ttsw.intership.soap_second_warehouse.ProductsList;
import pl.com.ttsw.intership.soapservice.services.SecondWarehouseService;

@Endpoint
public class SecondWarehouseEndpoint {

    private final SecondWarehouseService secondWarehouseService;

    public SecondWarehouseEndpoint(SecondWarehouseService secondWarehouseService) {
        this.secondWarehouseService = secondWarehouseService;
    }

    @PayloadRoot(namespace = "http://www.intership.ttsw.com.pl/soap-second-warehouse", localPart = "getAllProductsList")
    @ResponsePayload
    public GetAllProductsListResponse getAllWares() {
        ProductsList productsList = new ProductsList();
        productsList.getProduct().addAll(secondWarehouseService.getProductsList());
        GetAllProductsListResponse allResponse = new GetAllProductsListResponse();
        allResponse.setProductsList(productsList);
        return allResponse;
    }

}
