package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.WarehouseDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    Warehouse updateWarehouse(Warehouse warehouse1, @Context Warehouse warehouse2);

    @AfterMapping
    default Warehouse afterWarehouseMapping(@MappingTarget Warehouse warehouseInDatabase, @Context Warehouse warehouseUpdate) {
        if (nonNull(warehouseUpdate.getCity())) {
            warehouseInDatabase.setCity(warehouseUpdate.getCity());
        }
        if (nonNull(warehouseUpdate.getPostCode())) {
            warehouseInDatabase.setPostCode(warehouseUpdate.getPostCode());
        }
        if (nonNull(warehouseUpdate.getPhoneNumber())) {
            warehouseInDatabase.setPhoneNumber(warehouseUpdate.getPhoneNumber());
        }
        if (nonNull(warehouseUpdate.getStreet())) {
            warehouseInDatabase.setStreet(warehouseUpdate.getStreet());
        }
        return warehouseInDatabase;
    }

    WarehouseDto toWarehouseDto(Warehouse warehouse);
}
