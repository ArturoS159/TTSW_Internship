package pl.com.ttsw.intership.soapclient.client.repository.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.waressoapservice.models.Ware;

@Repository
public interface WareRepositoryMongoDB extends MongoRepository<Ware,String> {
}

