package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

//    Add new
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "employee_list";
    }
    // Show All
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_form";
    }
// Save
    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee employee) {
        service.save(employee);
        return "redirect:/employees";
    }

    // Change Information
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getById(id));
        return "employee_form";
    }

    // Change Status
    @GetMapping("/status/{id}")
    public String toggleStatus(@PathVariable("id") Integer id) {
        Employee emp = service.getById(id);
        if (emp != null) {
            emp.setStatus(!emp.isStatus());
            service.save(emp);
        }
        return "redirect:/employees";
    }
}
