package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Services.TSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        tsqlService.executeCustomTransaction(TSQLService.testQuery);
        return "redirect:/";
    }
}
