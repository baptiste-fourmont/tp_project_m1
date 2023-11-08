package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import com.m1csc.db.backend.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        return "EmployeeForm";
    }

    @PostMapping("")
    public String createEmployee(@ModelAttribute("employee") EmployeeEntity employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("")
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "Employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable BigInteger id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("/edit")
    public String updateEmployee(@ModelAttribute("employee") EmployeeEntity employee) {
        try {
            employeeService.getEmployeeById(employee.getIdEmployee()).get();
        } catch (Exception e) {
            return "redirect:/employees";
        }
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @DeleteMapping("/remove/{id}")
    public String deleteEmployee(@PathVariable BigInteger id) {
        EmployeeEntity employee = null;

        try{
            employee = employeeService.getEmployeeById(id).get();
        } catch (Exception e) {
            return "redirect:/employees";
        }

        employeeService.deleteEmployee(employee);
        return "redirect:/employees";
    }

}
