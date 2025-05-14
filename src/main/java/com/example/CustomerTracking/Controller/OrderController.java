package com.example.CustomerTracking.Controller;

import com.example.CustomerTracking.Entity.Order;
import com.example.CustomerTracking.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService OrderService;

    public OrderController(OrderService OrderService) {
        this.OrderService = OrderService;
    }

    @GetMapping
    public List<Order> getAll() {
        return OrderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.of(OrderService.getOrderById(id));
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return OrderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order updated) {
        return ResponseEntity.ok(OrderService.updateOrder(id, updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        OrderService.deleteOrder(id);
    }
}

