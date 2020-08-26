package pl.com.ttsw.intership.soapclient.client.service;

import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soapclient.client.connector.SOAPConnector;
import pl.com.ttsw.intership.soapclient.client.repository.mongo.StuffRepositoryMongoDB;
import pl.com.ttsw.intership.waressoapservice.models.GetAllStuffs;
import pl.com.ttsw.intership.waressoapservice.models.GetAllStuffsResponse;
import pl.com.ttsw.intership.waressoapservice.models.GetStuffByCode;
import pl.com.ttsw.intership.waressoapservice.models.GetStuffByCodeResponse;

@Service
public class StuffService {

    public static final String URL = "http://localhost:8080/soap-service";

    private final StuffRepositoryMongoDB stuffRepository;
    private final SOAPConnector soapConnector;

    public StuffService(StuffRepositoryMongoDB stuffRepository, SOAPConnector soapConnector) {
        this.stuffRepository = stuffRepository;
        this.soapConnector = soapConnector;
    }

    public void addStuffToMongoDB(Long id) {
        GetStuffByCode stuffRequest = new GetStuffByCode();
        stuffRequest.setCode(id);
        GetStuffByCodeResponse getStuffByCodeResponse =
                (GetStuffByCodeResponse) soapConnector.callWebService(URL, stuffRequest);
        stuffRepository.insert(getStuffByCodeResponse.getStuff());
    }

    public void addAllStuffToMongoDb() {
        GetAllStuffs allStuffsRequest = new GetAllStuffs();
        GetAllStuffsResponse getAllStuffsResponse =
                (GetAllStuffsResponse) soapConnector.callWebService(URL, allStuffsRequest);
        getAllStuffsResponse.getStuffs().getStuff().forEach(stuffRepository::insert);
    }
}
