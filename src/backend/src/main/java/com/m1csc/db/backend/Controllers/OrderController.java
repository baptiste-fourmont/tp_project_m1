package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.OrderEntity;
import com.m1csc.db.backend.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/new")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new OrderEntity());
        model.addAttribute("suppliers", orderService.getSuppliers());
        return "AddForms/addOrder";
    }

    @PostMapping("")
    public String createOrder(@ModelAttribute("order") OrderEntity order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("suppliers", orderService.getSuppliers());
        return "orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable Long id, Model model) {
        OrderEntity order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("order", order);
        return "EditForms/editOrder";
    }

    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute("order") OrderEntity order) {
        orderService.getOrderById(order.getIdOrder()).get();
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/remove/{id}")
    public String deleteOrder(@PathVariable Long id) {
        OrderEntity order = null;
        order = orderService.getOrderById(id).get();
        orderService.deleteOrder(order);
        return "redirect:/orders";
    }

}
