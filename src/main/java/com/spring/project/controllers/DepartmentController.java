package com.spring.project.controllers;

import com.spring.project.entity.Department;
import com.spring.project.entity.Entity;
import com.spring.project.service.DepartmentGroupService;
import com.spring.project.service.DepartmentService;
import com.spring.project.service.EntityService;
import com.spring.project.shared.PersistanceValidationGroup;
import com.spring.project.shared.SelectListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

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
    private Department department=new Department();
    private List<SelectListItem> entitySelectList=new ArrayList<>();
    private List<SelectListItem> departmentGroupSelectList=new ArrayList<>();

    @GetMapping(value = {"","/","/index"})
    public String index(Model model){
        model.addAttribute("departments",departmentService.departments());
        return "department/index";
    }

    @GetMapping("/edit")
    public String edit(Model model,@Nullable @RequestParam Integer id){
        department=departmentService.getDepartment(id);
        populateSelectList(model);
        model.addAttribute("department",department);
        model.addAttribute("isNew",department.getDepartmentCode()==0);
        return "department/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model,
                       @ModelAttribute("department") Department department,
                       @ModelAttribute("isNew") boolean isNew,
                       BindingResult bindingResult) {
        populateSelectList(model);

        validator.validate(department,bindingResult);
        departmentService.validate(department,isNew,bindingResult);
        model.addAttribute("department",department);
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "/department/edit";
        }
        departmentService.save(department);
        model.addAttribute("isNew",false);
        return "redirect:edit?id="+department.getDepartmentCode()+"&success";
    }

    public void populateSelectList(Model model){
        for(var entity : entityService.entites()){
            entitySelectList.add(new SelectListItem(
                    entity.getEntityName(),
                    String.valueOf(entity.getEntityId()),
                    entity.getEntityId()==department.getEntity().getEntityId()
            ));
        }
        model.addAttribute("entitySelectList",entitySelectList);
        for(var group : departmentGroupService.departmentGroups()){
            departmentGroupSelectList.add(new SelectListItem(
                    group.getName(),
                    String.valueOf(group.getGroupId()),
                    group.getGroupId()==department.getDepartmentGroup().getGroupId()
            ));
        }
        model.addAttribute("departmentGroupSelectList",departmentGroupSelectList);
    }

}
