package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.m1csc.db.backend.Services.CategoryService;



@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/new")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "AddForms/addCategory";
    }

    @PostMapping("")
    public String createCategory(@ModelAttribute("category") CategoryEntity category) {
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        CategoryEntity category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("category", category);
        return "EditForms/editCategory";
    }

    @PostMapping("/edit")
    public String updateCategory(@ModelAttribute("category") CategoryEntity category) {
        categoryService.getCategoryById(category.getIdCategory()).get();
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/remove/{id}")
    public String deleteCategory(@PathVariable Long id) {
        CategoryEntity category = null;
        category = categoryService.getCategoryById(id).get();
        categoryService.deleteCategory(category);

        return "redirect:/categories";
    }

}
