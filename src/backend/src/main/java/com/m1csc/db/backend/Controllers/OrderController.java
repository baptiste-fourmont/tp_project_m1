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
        return "OrderForm";
    }

    @PostMapping("")
    public String createOrder(@ModelAttribute("order") OrderEntity order) {
        orderService.createOrder(order);
        return "redirect:/Orders";
    }

    @GetMapping("")
    public String showOrders(Model model) {
        model.addAttribute("Orders", orderService.getOrders());
        return "Orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable Long id, Model model) {
        OrderEntity order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("order", order);
        return "editOrder";
    }

    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute("order") OrderEntity order) {
        try {
            orderService.getOrderById(order.getIdOrder()).get();
        } catch (Exception e) {
            return "redirect:/Orders";
        }
        orderService.updateOrder(order);
        return "redirect:/Orders";
    }

    @GetMapping("/remove/{id}")
    public String deleteOrder(@PathVariable Long id) {
        OrderEntity order = null;
        try{
            order = orderService.getOrderById(id).get();
            orderService.deleteOrder(order);
        } catch (Exception e) {
            return "redirect:/Orders";
        }

        return "redirect:/Orders";
    }


}
