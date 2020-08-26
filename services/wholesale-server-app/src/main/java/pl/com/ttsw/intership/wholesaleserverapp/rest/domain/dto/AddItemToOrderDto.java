package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;

import static pl.com.ttsw.intership.wholesaleserverapp.rest.statics.Static.*;

@Setter
@Getter
@NoArgsConstructor
public class AddItemToOrderDto {
    private String warehouse;
    private String productId;
    private Long amount;
    private String name;

    public AddItemToOrderDto(Row row) {
        this.warehouse = row.getCell(ZERO).toString();
        this.amount = (long) row.getCell(FOUR).getNumericCellValue();
        this.name = row.getCell(ONE).toString();
    }
}
