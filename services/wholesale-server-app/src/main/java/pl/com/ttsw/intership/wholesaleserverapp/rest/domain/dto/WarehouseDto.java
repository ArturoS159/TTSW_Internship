package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WarehouseDto {
    private Long warehouseId;
    private String warehouseName;
    private String street;
    private String postCode;
    private String city;
    private String nip;
    private String phoneNumber;
}
