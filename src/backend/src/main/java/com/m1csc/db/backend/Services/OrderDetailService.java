package com.m1csc.db.backend.Services;


import com.m1csc.db.backend.Entities.OrderDetailEntity;
import com.m1csc.db.backend.Entities.OrderEntity;
import com.m1csc.db.backend.Entities.ProductEntity;
import com.m1csc.db.backend.Repositories.OrderDetailRepository;
import com.m1csc.db.backend.Repositories.OrderRepository;
import com.m1csc.db.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderDetailEntity> getOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public void createOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    public void updateOrderDetail(OrderDetailEntity orderDetail) {
        OrderDetailEntity _orderDetail = orderDetailRepository.findById(orderDetail.getIdOrderDetail()).orElse(null);

        if (_orderDetail != null) {
            _orderDetail.setQuantity(orderDetail.getQuantity());
            _orderDetail.setProduct(orderDetail.getProduct());
            _orderDetail.setOrder(orderDetail.getOrder());
            _orderDetail.setOrder(orderDetail.getOrder());
            _orderDetail.setIdOrderDetail(orderDetail.getIdOrderDetail());
            orderDetailRepository.save(_orderDetail);
        }

        else
            throw new RuntimeException("Détail de commande introuvable");
    }

    public void deleteOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }

    public List<OrderDetailEntity> getOrderDetailsByOrderIdAndProductId(Long idOrder, Long idProduct) {
        return orderDetailRepository
                .findAll()
                .stream()
                .filter(
                        orderDetail -> orderDetail.getOrder().getIdOrder().equals(idOrder) &&
                                orderDetail.getProduct().getIdProduct().equals(idProduct)).toList();
    }

    public OrderDetailEntity getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }

    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }
}
