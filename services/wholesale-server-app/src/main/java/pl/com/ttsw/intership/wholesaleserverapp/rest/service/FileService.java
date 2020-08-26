package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.github.millij.poi.SpreadsheetReadException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void toCsv(Long userId, HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

    List<String> setBasketFromCsv(Long userId, MultipartFile file) throws IOException;

    ByteArrayInputStream toXlxs(Long userId, HttpServletResponse response, boolean group) throws IOException;

    List<String> setBasketFromXlxs(Long userId, MultipartFile file) throws IOException, SpreadsheetReadException, OpenXML4JException;
}
