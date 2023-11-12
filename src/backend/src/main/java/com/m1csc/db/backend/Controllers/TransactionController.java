package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.Enums.TransactionType;
import com.m1csc.db.backend.Entities.TransactionEntity;
import com.m1csc.db.backend.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ModelAttribute("transaction_buy")
    public TransactionType transactionTypeBuy() {
        return TransactionType.BUY;
    }

    @ModelAttribute("transaction_sell")
    public TransactionType transactionTypeSell() {
        return TransactionType.SELL;
    }

    @GetMapping("/new")
    public String showTransactionForm(Model model) {
        model.addAttribute("transaction", new TransactionEntity());
        model.addAttribute("products", transactionService.getProducts());
        model.addAttribute("employees", transactionService.getEmployees());
        return "AddForms/addTransaction";
    }

    @PostMapping("")
    public String createTransaction(@ModelAttribute("transaction") TransactionEntity transaction) {
        transactionService.createTransaction(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("")
    public String showTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("products", transactionService.getProducts());
        model.addAttribute("employees", transactionService.getEmployees());
        return "transactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditTransactionForm(@PathVariable Long id, Model model) {
        TransactionEntity transaction = transactionService.getTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("transaction", transaction);
        model.addAttribute("products", transactionService.getProducts());
        model.addAttribute("employees", transactionService.getEmployees());
        return "EditForms/editTransaction";
    }

    @PostMapping("/edit")
    public String updateTransaction(@ModelAttribute("transaction") TransactionEntity transaction) {
        transactionService.getTransactionById(transaction.getIdTransaction()).get();
        transactionService.updateTransaction(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/remove/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        TransactionEntity transaction = null;
        transaction = transactionService.getTransactionById(id).get();
        transactionService.deleteTransaction(transaction);

        return "redirect:/transactions";
    }
}
