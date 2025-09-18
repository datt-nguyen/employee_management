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

    // Hiển thị tất cả
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "employee_list";
    }

    // Form thêm mới
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee employee) {
        service.save(employee);
        return "redirect:/employees";
    }

    // Sửa thông tin
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getById(id));
        return "employee_form";
    }

    // Thay đổi trạng thái
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
