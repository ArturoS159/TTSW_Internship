package pl.com.ttsw.intership.soapclient.client.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.soapclient.client.entity.XmlFile;

@Repository
public interface XmlFileRepositoryPostgres extends JpaRepository<XmlFile, Long> {
}
