package pl.com.ttsw.intership.soapservice.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.com.ttsw.intership.soap_first_warehouse.GetAllWaresResponse;
import pl.com.ttsw.intership.soap_first_warehouse.WaresList;
import pl.com.ttsw.intership.soapservice.services.FirstWarehouseService;

@Endpoint
public class FirstWarehouseEndpoint {

    private final FirstWarehouseService firstWarehouseService;

    public FirstWarehouseEndpoint(FirstWarehouseService firstWarehouseService) {
        this.firstWarehouseService = firstWarehouseService;
    }

    @PayloadRoot(namespace = "http://www.intership.ttsw.com.pl/soap-first-warehouse", localPart = "getAllWares")
    @ResponsePayload
    public GetAllWaresResponse getAllWares() {
        WaresList waresList = new WaresList();
        waresList.getWare().addAll(firstWarehouseService.getWaresList());
        GetAllWaresResponse allResponse = new GetAllWaresResponse();
        allResponse.setWaresList(waresList);
        return allResponse;
    }
}
