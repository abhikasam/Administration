package com.spring.project.controllers;

import com.spring.project.entity.Department;
import com.spring.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private Validator validator;

    private Department department;
    @ModelAttribute(value = "department")
    public void setDepartment(Department department){
        this.department=department;
    }
    @GetMapping(value = {"","/","/index"})
    public String index(Model model){
        model.addAttribute("departments",departmentService.departments());
        return "department/index";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        model.addAttribute("department",department);
        return "department/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, BindingResult bindingResult, Errors errors) {
        validator.validate(department,errors);
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "/department/edit";
        }

        return "redirect:/department/edit?success";
    }

}
