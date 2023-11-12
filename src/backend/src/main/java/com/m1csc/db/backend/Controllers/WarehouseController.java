package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.WarehouseEntity;
import com.m1csc.db.backend.Services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/new")
    public String showWarehouseForm(Model model) {
        model.addAttribute("warehouse", new WarehouseEntity());
        return "AddForms/addWarehouse";
    }

    @PostMapping("")
    public String createWarehouse(@ModelAttribute("warehouse") WarehouseEntity warehouse) {
        warehouseService.createWarehouse(warehouse);
        return "redirect:/warehouses";
    }

    @GetMapping("")
    public String showWarehouses(Model model) {
        model.addAttribute("Warehouses", warehouseService.getWarehouses());
        return "warehouses";
    }

    @GetMapping("/edit/{id}")
    public String showEditWarehouseForm(@PathVariable Long id, Model model) {
        WarehouseEntity warehouse = warehouseService.getWarehouseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("warehouse", warehouse);
        return "EditForms/editWarehouse";
    }

    @PostMapping("/edit")
    public String updateWarehouse(@ModelAttribute("warehouse") WarehouseEntity warehouse) {

        warehouseService.getWarehouseById(warehouse.getIdWarehouse()).get();
        warehouseService.updateWarehouse(warehouse);
        return "redirect:/warehouses";
    }

    @GetMapping("/remove/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        WarehouseEntity warehouse = null;
        warehouse = warehouseService.getWarehouseById(id).get();
        warehouseService.deleteWarehouse(warehouse);

        return "redirect:/warehouses";
    }


}
