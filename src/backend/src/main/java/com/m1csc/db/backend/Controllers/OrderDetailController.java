package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.OrderDetailEntity;
import com.m1csc.db.backend.Services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/order/detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/new")
    public String showOrderDetailForm(Model model) {
        model.addAttribute("orderdetail", new OrderDetailEntity());
        return "OrderDetailForm";
    }

    @PostMapping("")
    public String createOrderDetail(@ModelAttribute("OrderDetail") OrderDetailEntity orderDetail) {
        orderDetailService.createOrderDetail(orderDetail);
        return "redirect:/orderdetail";
    }

    @GetMapping("")
    public String showOrderDetails(Model model) {
        model.addAttribute("orderdetail", orderDetailService.getOrderDetails());
        return "orderdetail";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderDetailForm(@PathVariable BigInteger id, Model model) {
        OrderDetailEntity orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail == null)
            throw new IllegalArgumentException("Employé non trouvé avec l'ID: " + id);
        model.addAttribute("orderdetail", orderDetail);
        return "editOrderDetail";
    }

    @PostMapping("/edit")
    public String updateOrderDetail(@ModelAttribute("orderdetail") OrderDetailEntity orderDetail) {
        try {
            orderDetailService.getOrderDetailById(orderDetail.getIdOrderDetail());
        } catch (Exception e) {
            return "redirect:/orderdetail";
        }
        orderDetailService.updateOrderDetail(orderDetail);
        return "redirect:/orderdetail";
    }

    @DeleteMapping("/remove/{id}")
    public String deleteOrderDetail(@PathVariable BigInteger id) {
        OrderDetailEntity orderDetail = null;

        try{
            orderDetail = orderDetailService.getOrderDetailById(id);
            orderDetailService.deleteOrderDetail(orderDetail);
        } catch (Exception e) {
            return "redirect:/orderdetail";
        }

        return "redirect:/order/detail";
    }

}
