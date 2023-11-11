package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.SupplierEntity;
import com.m1csc.db.backend.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/suppliers")  
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;


    @GetMapping("/new")
    public String showSupplierForm(Model model) {
        model.addAttribute("supplier", new SupplierEntity());
        return "SupplierForm";
    }

    @PostMapping("")
    public String createSupplier(@ModelAttribute("supplier") SupplierEntity supplier) {
        supplierService.createSupplier(supplier);
        return "redirect:/Suppliers";
    }

    @GetMapping("")
    public String showSuppliers(Model model) {
        model.addAttribute("Suppliers", supplierService.getSuppliers());
        return "Suppliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditSupplierForm(@PathVariable BigInteger id, Model model) {
        SupplierEntity supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("supplier", supplier);
        return "editSupplier";
    }

    @PostMapping("/edit")
    public String updateSupplier(@ModelAttribute("supplier") SupplierEntity supplier) {
        try {
            supplierService.getSupplierById(supplier.getIdSupplier()).get();
        } catch (Exception e) {
            return "redirect:/Suppliers";
        }
        supplierService.updateSupplier(supplier);
        return "redirect:/Suppliers";
    }

    @DeleteMapping("/remove/{id}")
    public String deleteSupplier(@PathVariable BigInteger id) {
        SupplierEntity supplier = null;
        try{
            supplier = supplierService.getSupplierById(id).get();
            supplierService.deleteSupplier(supplier);
        } catch (Exception e) {
            return "redirect:/Suppliers";
        }

        return "redirect:/Suppliers";
    }


}