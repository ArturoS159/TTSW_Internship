package pl.com.ttsw.intership.soapservice.endpoints;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.com.ttsw.intership.soap_third_warehouse.GetAllProductsResponse;
import pl.com.ttsw.intership.soap_third_warehouse.ProductsFromWarehouse;
import pl.com.ttsw.intership.soapservice.services.ThirdWarehouseService;

@Endpoint
public class ThirdWarehouseEndpoint {

    private final ThirdWarehouseService thirdWarehouseService;

    public ThirdWarehouseEndpoint(ThirdWarehouseService thirdWarehouseService) {
        this.thirdWarehouseService = thirdWarehouseService;
    }

    @PayloadRoot(namespace = "http://www.intership.ttsw.com.pl/soap-third-warehouse", localPart = "getAllProducts")
    @ResponsePayload
    public GetAllProductsResponse getAllWares() {
        ProductsFromWarehouse products = new ProductsFromWarehouse();
        products.getProduct().addAll(thirdWarehouseService.getProductsList());
        GetAllProductsResponse allResponse = new GetAllProductsResponse();
        allResponse.setProducts(products);
        return allResponse;
    }

}
