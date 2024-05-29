package com.spring.project.controllers;

import com.spring.project.entity.Department;
import com.spring.project.entity.Entity;
import com.spring.project.service.DepartmentGroupService;
import com.spring.project.service.DepartmentService;
import com.spring.project.service.EntityService;
import com.spring.project.shared.PersistanceValidationGroup;
import com.spring.project.shared.SelectListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private DepartmentGroupService departmentGroupService;
    @Autowired
    private Validator validator;

    private List<SelectListItem> entitySelectList=new ArrayList<>();
//    @ModelAttribute("entitySelectList")
//    public void setEntitySelectList(ArrayList<SelectListItem> items){
//        this.entitySelectList=items;
//    }
    private List<SelectListItem> departmentGroupSelectList=new ArrayList<>();
//    @ModelAttribute("departmentGroupSelectList")
//    public void setDepartmentGroupSelectList(ArrayList<SelectListItem> items){
//        this.departmentGroupSelectList=items;
//    }

    @GetMapping(value = {"","/","/index"})
    public String index(Model model){
        model.addAttribute("departments",departmentService.departments());
        return "department/index";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        populateSelectList(model);
        model.addAttribute("department",new Department());
        return "department/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model,
                       @ModelAttribute("department") Department department,
                       BindingResult bindingResult) {
        populateSelectList(model);

        validator.validate(department,bindingResult);
        departmentService.validate(department,bindingResult);
        model.addAttribute("department",department);
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "/department/edit";
        }
        departmentService.save(department);
        return "redirect:department/edit?success";
    }

    public void populateSelectList(Model model){
        for(var entity : entityService.entites()){
            entitySelectList.add(new SelectListItem(
                    entity.getEntityName(),
                    String.valueOf(entity.getEntityId()),
                    entity.getEntityId()==0
            ));
        }
        model.addAttribute("entitySelectList",entitySelectList);
        for(var group : departmentGroupService.departmentGroups()){
            departmentGroupSelectList.add(new SelectListItem(
                    group.getName(),
                    String.valueOf(group.getGroupId()),
                    group.getGroupId()==0
            ));
            model.addAttribute("departmentGroupSelectList",departmentGroupSelectList);
        }
    }

}
