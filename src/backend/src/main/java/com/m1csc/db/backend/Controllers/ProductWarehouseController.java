package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import com.m1csc.db.backend.Entities.ProductWarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.m1csc.db.backend.Services.ProductWarehouseService;

import java.math.BigInteger;

@Controller
@RequestMapping("/product/warehouse")
public class ProductWarehouseController {

    @Autowired
    private ProductWarehouseService productWarehouseService;

    @GetMapping("")
    public String showProductWarehouses() {
        return "ProductWarehouses";
    }

    @GetMapping("/new")
    public String showProductWarehouseForm() {
        return "ProductWarehouseForm";
    }

    @PutMapping("")
    public String createProductWarehouse(@ModelAttribute("productWarehouse") ProductWarehouseEntity productWarehouse) {
        productWarehouseService.createProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }


    @DeleteMapping("/remove/{id}")
    public String deleteProductWarehouse(@PathVariable BigInteger id) {
        ProductWarehouseEntity productWarehouse = null;

        try{
            productWarehouse = productWarehouseService.getProductWarehouseById(id).get();
        } catch (Exception e) {
            return "redirect:/product/warehouse";
        }

        productWarehouseService.deleteProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductWarehouseForm(@PathVariable BigInteger id, Model model) {
        ProductWarehouseEntity productWarehouse = productWarehouseService.getProductWarehouseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec l'ID: " + id));
        model.addAttribute("productWarehouse", productWarehouse);
        return "editProductWarehouse";
    }

    @PostMapping("/edit")
    public String updateProductWarehouseController(@ModelAttribute("productWarehouse") ProductWarehouseEntity productWarehouse) {
        try {
            productWarehouseService.getProductWarehouseById(productWarehouse.getIdProductWarehouse()).get();
        } catch (Exception e) {
            return "redirect:/product/warehouse";
        }
        productWarehouseService.updateProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }

}
