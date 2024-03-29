package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.CategoryEntity;
import com.m1csc.db.backend.Entities.ProductEntity;
import com.m1csc.db.backend.Entities.SupplierEntity;
import com.m1csc.db.backend.Services.CategoryService;
import com.m1csc.db.backend.Services.ProductService;
import com.m1csc.db.backend.Services.SupplierService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/new")
    public String showProductForm(Model model) {
        List<CategoryEntity> categories = categoryService.getCategories();
        List<SupplierEntity> suppliers = supplierService.getSuppliers();
        model.addAttribute("categories", categories);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", new ProductEntity());
        return "AddForms/addProduct";
    }

    @PostMapping("")
    public String createProduct(@ModelAttribute("product") ProductEntity product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        ProductEntity product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "EditForms/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") ProductEntity product) {
        productService.getProductById(product.getIdProduct()).get();
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/remove/{id}")
    public String deleteProduct(@PathVariable Long id) {
        ProductEntity product = null;

        product = productService.getProductById(id).get();

        productService.deleteProduct(product);
        return "redirect:/products";
    }

}
