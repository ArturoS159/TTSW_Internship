package pl.com.ttsw.intership.wholesaleserverapp.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.OrderService;

@RestController
@RequestMapping("/rest-service/order")
@CrossOrigin("*")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("#userId == principal.id")
    @PostMapping("/{userId}/finish")
    public ResponseEntity<Void> finishOrder(@PathVariable Long userId,
                                            @RequestParam(value = "warehouse", required = false) String warehouseName) {
        orderService.finishOrder(userId, warehouseName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping("/{userId}/all")
    public ResponseEntity<Page<OrderDto>> getAllOrders(@PathVariable Long userId,
                                                       @ModelAttribute("orderDto") OrderDto orderDto,
                                                       Pageable pageable) {
        return new ResponseEntity<>(orderService.getAllOrders(userId, orderDto, pageable), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping("/{userId}/total")
    public ResponseEntity<TotalElements> getTotalElements(@PathVariable Long userId,
                                                          @RequestParam(required = false) boolean active) {
        return new ResponseEntity<>(orderService.getTotalElements(userId, active), HttpStatus.OK);
    }
}
