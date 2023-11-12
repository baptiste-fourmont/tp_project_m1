package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.ProductWarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.m1csc.db.backend.Services.ProductWarehouseService;



@Controller
@RequestMapping("/product/warehouse")
public class ProductWarehouseController {

    @Autowired
    private ProductWarehouseService productWarehouseService;

    @GetMapping("")
    public String showProductWarehouses(Model model) {
        model.addAttribute("productWarehouses", productWarehouseService.getProductWarehouses());
        return "productWarehouses";
    }

    @GetMapping("/new")
    public String showProductWarehouseForm(Model model) {
        model.addAttribute("productWarehouse", new ProductWarehouseEntity());
        model.addAttribute("products", productWarehouseService.getAllProducts());
        model.addAttribute("warehouses", productWarehouseService.getAllWarehouses());
        return "AddForms/addProductWarehouse";
    }

    @PostMapping("")
    public String createProductWarehouse(@ModelAttribute("productWarehouse") ProductWarehouseEntity productWarehouse) {
        productWarehouseService.createProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }


    @GetMapping("/remove/{id}")
    public String deleteProductWarehouse(@PathVariable Long id) {
        ProductWarehouseEntity productWarehouse = null;

        productWarehouse = productWarehouseService.getProductWarehouseById(id).get();
        productWarehouseService.deleteProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductWarehouseForm(@PathVariable Long id, Model model) {
        ProductWarehouseEntity productWarehouse = productWarehouseService.getProductWarehouseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec l'ID: " + id));
        model.addAttribute("productWarehouse", productWarehouse);
        model.addAttribute("products", productWarehouseService.getAllProducts());
        model.addAttribute("warehouses", productWarehouseService.getAllWarehouses());
        return "EditForms/editProductWarehouse";
    }

    @PostMapping("/edit")
    public String updateProductWarehouseController(@ModelAttribute("productWarehouse") ProductWarehouseEntity productWarehouse) {
        productWarehouseService.getProductWarehouseById(productWarehouse.getIdProductWarehouse()).get();

        productWarehouseService.updateProductWarehouse(productWarehouse);
        return "redirect:/product/warehouse";
    }

}
