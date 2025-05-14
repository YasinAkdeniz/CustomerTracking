package com.example.CustomerTracking.Service;

import com.example.CustomerTracking.Entity.Order;
import com.example.CustomerTracking.Repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public Order saveOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        return orderRepo.save(order);
    }

    public Order updateOrder(Long id, Order updated) {
        Order existing = orderRepo.findById(id).orElseThrow();
        existing.setCustomer(updated.getCustomer());
        existing.setProduct(updated.getProduct());
        existing.setQuantity(updated.getQuantity());
        existing.setTotalPrice(updated.getTotalPrice());
        existing.setOrderDate(updated.getOrderDate());
        return orderRepo.save(existing);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }
}
