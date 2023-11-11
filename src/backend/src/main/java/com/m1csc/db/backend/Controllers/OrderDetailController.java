package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.OrderDetailEntity;
import com.m1csc.db.backend.Services.OrderDetailService;
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
        return "OrderDetailForm";
    }

    @PostMapping("")
    public String createOrderDetail(@ModelAttribute("orderdetail") OrderDetailEntity orderDetail) {
        orderDetailService.createOrderDetail(orderDetail);
        return "redirect:/order/detail";
    }

    @GetMapping("")
    public String showOrderDetails(Model model) {
        model.addAttribute("orderdetail", orderDetailService.getOrderDetails());
        model.addAttribute("products", orderDetailService.getProducts());
        model.addAttribute("orders", orderDetailService.getOrders());
        return "orderDetail";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderDetailForm(@PathVariable Long id, Model model) {
        OrderDetailEntity orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail == null)
            throw new IllegalArgumentException("Employé non trouvé avec l'ID: " + id);
        model.addAttribute("orderdetail", orderDetail);
        model.addAttribute("products", orderDetailService.getProducts());
        model.addAttribute("orders", orderDetailService.getOrders());
        return "editOrderDetail";
    }

    @PostMapping("/edit")
    public String updateOrderDetail(@ModelAttribute("orderdetail") OrderDetailEntity orderDetail) {
        try {
            orderDetailService.getOrderDetailById(orderDetail.getIdOrderDetail());
        } catch (Exception e) {
            return "redirect:/order/detail";
        }
        orderDetailService.updateOrderDetail(orderDetail);
        return "redirect:/order/detail";
    }

    @GetMapping("/remove/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        OrderDetailEntity orderDetail = null;

        try{
            orderDetail = orderDetailService.getOrderDetailById(id);
            orderDetailService.deleteOrderDetail(orderDetail);
        } catch (Exception e) {
            return "redirect:/order/detail";
        }

        return "redirect:/order/detail";
    }

}
