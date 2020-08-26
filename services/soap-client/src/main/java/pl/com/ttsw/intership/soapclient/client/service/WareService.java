package pl.com.ttsw.intership.soapclient.client.service;

import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soapclient.client.connector.SOAPConnector;
import pl.com.ttsw.intership.soapclient.client.repository.mongo.WareRepositoryMongoDB;
import pl.com.ttsw.intership.waressoapservice.models.GetAllWares;
import pl.com.ttsw.intership.waressoapservice.models.GetAllWaresResponse;
import pl.com.ttsw.intership.waressoapservice.models.GetWareById;
import pl.com.ttsw.intership.waressoapservice.models.GetWareByIdResponse;

@Service
public class WareService {

    public static final String URL = "http://localhost:8080/soap-service";

    private final WareRepositoryMongoDB wareRepositoryMongoDB;
    private final SOAPConnector soapConnector;

    public WareService(WareRepositoryMongoDB wareRepositoryMongoDB, SOAPConnector soapConnector) {
        this.wareRepositoryMongoDB = wareRepositoryMongoDB;
        this.soapConnector = soapConnector;
    }

    public void addWareToMongoDB(Long id) {
        GetWareById wareRequest = new GetWareById();
        wareRequest.setId(id);
        GetWareByIdResponse getWareByIdResponse =
                (GetWareByIdResponse) soapConnector.callWebService(URL, wareRequest);
        wareRepositoryMongoDB.insert(getWareByIdResponse.getWare());
    }

    public void addAllWaresToMongoDB() {
        GetAllWares allWaresRequest = new GetAllWares();
        GetAllWaresResponse getAllWaresResponse =
                (GetAllWaresResponse) soapConnector.callWebService(URL, allWaresRequest);
        getAllWaresResponse.getWareList().getWare().forEach(wareRepositoryMongoDB::insert);
    }

}
