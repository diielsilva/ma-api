package com.todev.ma.order;

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
    void create(Authentication session, CreateOrderRequestData data) {
        orderService.create(session, data);
    }

    @GetMapping
    @ResponseStatus(value = OK)
    List<DisplayOrderResponseData> findAll(Authentication session) {
        return orderService.findAll(session);
    }
}
