package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.OrderDetailEntity;
import com.m1csc.db.backend.Services.OrderDetailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order/detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/new")
    public String showOrderDetailForm(Model model) {
        model.addAttribute("orderdetail", new OrderDetailEntity());
        model.addAttribute("products", orderDetailService.getProducts());
        model.addAttribute("orders", orderDetailService.getOrders());
        return "AddForms/addOrderDetail";
    }

    @PostMapping("")
    public String createOrderDetail(@ModelAttribute("orderdetail") OrderDetailEntity orderDetail) {
        orderDetailService.createOrderDetail(orderDetail);
        return "redirect:/order/detail";
    }

    @GetMapping("")
    public String showOrderDetails(Model model) {
        List<OrderDetailEntity> orderDetails = orderDetailService.getOrderDetails();
        model.addAttribute("orderdetails", orderDetails);
        return "orderDetails"; // Replace with the name of your HTML template
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderDetailForm(@PathVariable Long id, Model model) {
        OrderDetailEntity orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail == null)
            throw new IllegalArgumentException("Employé non trouvé avec l'ID: " + id);
        model.addAttribute("orderdetail", orderDetail);
        model.addAttribute("products", orderDetailService.getProducts());
        model.addAttribute("orders", orderDetailService.getOrders());
        return "EditForms/editOrderDetail";
    }

    @PostMapping("/edit")
    public String updateOrderDetail(@ModelAttribute("orderdetail") OrderDetailEntity orderDetail) {
        orderDetailService.getOrderDetailById(orderDetail.getIdOrderDetail());
        orderDetailService.updateOrderDetail(orderDetail);
        return "redirect:/order/detail";
    }

    @GetMapping("/remove/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        OrderDetailEntity orderDetail = null;
        orderDetail = orderDetailService.getOrderDetailById(id);
        orderDetailService.deleteOrderDetail(orderDetail);

        return "redirect:/order/detail";
    }
}
