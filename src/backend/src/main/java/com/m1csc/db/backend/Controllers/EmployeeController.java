package com.m1csc.db.backend.Controllers;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import com.m1csc.db.backend.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        model.addAttribute("warehouses", employeeService.getWarehouses());
        return "AddForms/addEmployee";
    }

    @PostMapping("")
    public String createEmployee(@ModelAttribute("employee") EmployeeEntity employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("")
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("warehouses", employeeService.getWarehouses());
        return "employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + id));
        model.addAttribute("employee", employee);
        model.addAttribute("warehouses", employeeService.getWarehouses());
        return "EditForms/editEmployee";
    }

    @PostMapping("/edit")
    public String updateEmployee(@ModelAttribute("employee") EmployeeEntity employee) {
        employeeService.getEmployeeById(employee.getIdEmployee()).get();
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/remove/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        EmployeeEntity employee = null;
        employee = employeeService.getEmployeeById(id).get();
        employeeService.deleteEmployee(employee);
        return "redirect:/employees";
    }

}
