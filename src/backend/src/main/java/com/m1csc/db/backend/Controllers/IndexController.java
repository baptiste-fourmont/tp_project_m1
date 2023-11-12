package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Services.TSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class IndexController {

    @Autowired
    TSQLService tsqlService;

    @GetMapping("/")
    public String show() {
        return "index";
    }

    @GetMapping("/custom")
    public String showTransactions() {
        tsqlService.executeCustomTransaction(TSQLService.TEST_QUERY);
        return "redirect:/";
    }

    @GetMapping("/custom/addstock")
    public String showAddStock(Model model) throws IOException {
        String query = tsqlService.readFile(TSQLService.ADD_TRANSACTION);
        model.addAttribute("query", query);
        tsqlService.executeCustomTransaction(query);
        return "customSQL";
    }

    @GetMapping("/custom/modstock")
    public String showModStock(Model model) throws IOException {
        String query = tsqlService.readFile(TSQLService.MOD_TRANSACTION);
        model.addAttribute("query", query);
        tsqlService.executeCustomTransaction(query);
        return "customSQL";
    }

    @GetMapping("/custom/delstock")
    public String showDelStock(Model model) throws IOException {
        String query = tsqlService.readFile(TSQLService.DEL_TRANSACTION);
        model.addAttribute("query", query);
        tsqlService.executeCustomTransaction(query);
        return "customSQL";
    }
}
