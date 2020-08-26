package pl.com.ttsw.intership.soapservice.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.com.ttsw.intership.soap_fifth_warehouse.GetAllProductsInWarehouseResponse;
import pl.com.ttsw.intership.soap_fifth_warehouse.ProductsWarehouseList;
import pl.com.ttsw.intership.soapservice.services.FifthWarehouseService;

@Endpoint
public class FifthWarehouseEndpoint {

    private final FifthWarehouseService fifthWarehouseService;

    public FifthWarehouseEndpoint(FifthWarehouseService fifthWarehouseService) {
        this.fifthWarehouseService = fifthWarehouseService;
    }

    @PayloadRoot(namespace = "http://www.intership.ttsw.com.pl/soap-fifth-warehouse",
            localPart = "getAllProductsInWarehouse")
    @ResponsePayload
    public GetAllProductsInWarehouseResponse getAllProductsInWarehouse() {
        ProductsWarehouseList warehouseList = new ProductsWarehouseList();
        warehouseList.getWarehouseProduct().addAll(fifthWarehouseService.getList());
        GetAllProductsInWarehouseResponse response = new GetAllProductsInWarehouseResponse();
        response.setProductsWarehouseList(warehouseList);
        return response;
    }


}
