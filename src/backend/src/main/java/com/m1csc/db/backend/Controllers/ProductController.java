package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import com.m1csc.db.backend.Entities.ProductEntity;
import com.m1csc.db.backend.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new EmployeeEntity());
        return "ProductForm";
    }

    @PostMapping("")
    public String createProduct(@ModelAttribute("product") ProductEntity product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "Products";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        ProductEntity product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID: " + id));
        model.addAttribute("product", product);
        return "editProduct.html";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") ProductEntity product) {
        try {
            productService.getProductById(product.getIdProduct()).get();
        } catch (Exception e) {
            return "redirect:/products";
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/remove/{id}")
    public String deleteProduct(@PathVariable Long id) {
        ProductEntity product = null;

        try{
            product = productService.getProductById(id).get();
        } catch (Exception e) {
            return "redirect:/products";
        }

        productService.deleteProduct(product);
        return "redirect:/products";
    }

}
