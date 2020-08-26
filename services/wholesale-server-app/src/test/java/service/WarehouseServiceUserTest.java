package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.WarehouseRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.UserNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.WarehouseAlreadyExistException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.WarehouseNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl.WarehouseServiceImpl;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceUserTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private Warehouse prepareWarehouse(String warehouseName){
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName(warehouseName);
        return warehouse;
    }

    @Test
    void shouldThrowExceptionUserNotFound(){
        //given
        Warehouse warehouse = prepareWarehouse("nam");

        //then
        Assertions.assertThrows(UserNotFoundException.class,() -> warehouseService.addWarehouseForUser(1L,warehouse));
    }

    @Test
    void shouldThrowExceptionWarehouseAlreadyExist(){
        //given
        Warehouse warehouse1 = prepareWarehouse("nam");
        Warehouse warehouse2 = prepareWarehouse("nam");
        given(warehouseRepository.findByWarehouseName(warehouse2.getWarehouseName())).willReturn(Optional.of(warehouse1));
        given(userRepository.findById(1L)).willReturn(Optional.of(new User()));

        //then
        Assertions.assertThrows(WarehouseAlreadyExistException.class,() -> warehouseService.addWarehouseForUser(1L,warehouse1));
    }
}
