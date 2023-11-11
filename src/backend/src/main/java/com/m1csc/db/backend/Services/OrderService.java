package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.OrderEntity;
import com.m1csc.db.backend.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    public void createOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    public void updateOrder(OrderEntity order) {
        OrderEntity _order = orderRepository.findById(order.getIdOrder()).orElse(null);

        if (_order != null) {
            _order.setDateOrder(order.getDateOrder());
            _order.setIdOrder(order.getIdOrder());
            _order.setSupplier(order.getSupplier());
            orderRepository.save(_order);
        }

        else
            throw new RuntimeException("Commande introuvable");
    }

    public void deleteOrder(OrderEntity order) {
        orderRepository.delete(order);
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
