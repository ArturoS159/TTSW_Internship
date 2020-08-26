package pl.com.ttsw.intership.wholesaleserverapp.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UpdateOrderStatusDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.WarehouseDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.WarehouseService;

@RestController
@RequestMapping("/rest-service/warehouse")
@CrossOrigin("*")
@PreAuthorize("hasRole('WORKER')")
@AllArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> createWarehouse(@PathVariable Long userId,
                                                @RequestBody Warehouse warehouse) {
        warehouseService.addWarehouseForUser(userId, warehouse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @PutMapping("/{userId}/{warehouseId}/update-data")
    public ResponseEntity<Void> updateWarehouseData(@PathVariable Long userId,
                                                    @PathVariable Long warehouseId,
                                                    @RequestBody Warehouse warehouse) {
        warehouseService.updateWarehouseData(warehouseId, warehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/{warehouseId}/order/all")
    public ResponseEntity<Page<OrderDto>> getAllOrdersInWarehouse(@PathVariable Long userId,
                                                                  @PathVariable Long warehouseId,
                                                                  @ModelAttribute("orderDto") OrderDto orderDto,
                                                                  Pageable pageable) {
        return new ResponseEntity<>(warehouseService.getAllOrdersInWarehouse(userId, warehouseId, orderDto, pageable), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @GetMapping("/{userId}/all")
    public ResponseEntity<Page<WarehouseDto>> getAllOrdersInWarehouse(@PathVariable Long userId,
                                                                      Pageable pageable) {
        return new ResponseEntity<>(warehouseService.getAllWarehouse(userId, pageable), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @GetMapping("/{userId}/{warehouseId}/worker/all")
    public ResponseEntity<Page<UserDto>> getAllWorkersInWarehouse(@PathVariable Long userId,
                                                                  @PathVariable Long warehouseId,
                                                                  Pageable pageable) {
        return new ResponseEntity<>(warehouseService.getAllWorkersInWarehouse(warehouseId, pageable), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{warehouseId}/update/{orderId}")
    public ResponseEntity<Void> updateOrderStatusInWarehouse(@PathVariable Long warehouseId,
                                                             @PathVariable Long orderId,
                                                             @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        warehouseService.updateOrderStatusInWarehouse(warehouseId, orderId, updateOrderStatusDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @DeleteMapping("/{userId}/{warehouseId}/del/{workerId}")
    public ResponseEntity<Void> deleteUserFromWarehouse(@PathVariable Long userId,
                                                        @PathVariable Long warehouseId,
                                                        @PathVariable Long workerId) {
        warehouseService.deleteUserFromWarehouse(warehouseId, workerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id && hasRole('WAREHOUSE_OWNER')")
    @PostMapping("/{userId}/{warehouseId}/add-worker")
    public ResponseEntity<Void> addUserToWarehouse(@PathVariable Long userId,
                                                   @PathVariable Long warehouseId,
                                                   @RequestBody UserDto userDto) {
        warehouseService.addUserToWarehouse(warehouseId, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @PostMapping("/{userId}/{orderId}/assign")
    public ResponseEntity<Void> assignOrderAsMe(@PathVariable Long userId,
                                                @PathVariable Long orderId) {
        warehouseService.assignOrderAsMe(orderId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @PostMapping("/{userId}/{orderId}/un-assign")
    public ResponseEntity<Void> UnAssignOrder(@PathVariable Long userId,
                                              @PathVariable Long orderId) {
        warehouseService.unAssignOrder(orderId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}