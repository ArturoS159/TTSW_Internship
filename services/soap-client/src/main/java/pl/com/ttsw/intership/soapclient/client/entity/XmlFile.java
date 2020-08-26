package pl.com.ttsw.intership.soapclient.client.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "xml_files")
public class XmlFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "xml_file")
    private String file;
    @Column(name = "date")
    private LocalDate date;

    public XmlFile() {
    }

    public Long getId() {
        return id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WaresXml{" +
                "id=" + id +
                ", file=" + file +
                ", date=" + date +
                '}';
    }
}
