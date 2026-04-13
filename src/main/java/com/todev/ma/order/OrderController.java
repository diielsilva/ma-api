package com.todev.ma.order;

import com.todev.ma.order.pix.DisplayPixDetailResponseData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/orders")
class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(value = CREATED)
    void create(Authentication session, @RequestBody CreateOrderRequestData data) {
        orderService.create(session, data);
    }

    @GetMapping
    @ResponseStatus(value = OK)
    List<DisplayOrderResponseData> findAll(Authentication session) {
        return orderService.findAll(session);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping(value = "/search")
    @ResponseStatus(value = OK)
    List<DisplayOrderResponseData> findAllByUserCpf(@RequestParam String cpf) {
        return orderService.findAllByUserCpf(cpf);
    }

    @GetMapping(value = "/{orderId}/details")
    @ResponseStatus(value = OK)
    DisplayPixDetailResponseData findDetailsByOrderId(Authentication session, @PathVariable Long orderId) {
        return orderService.findPixDetailsByOrderId(session, orderId);
    }
}
