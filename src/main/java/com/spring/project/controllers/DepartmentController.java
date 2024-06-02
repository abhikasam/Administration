package com.spring.project.controllers;

import com.spring.project.entity.Department;
import com.spring.project.entity.Entity;
import com.spring.project.enums.SortingType;
import com.spring.project.filter.DepartmentFilter;
import com.spring.project.service.*;
import com.spring.project.shared.Pagination;
import com.spring.project.shared.PersistanceValidationGroup;
import com.spring.project.shared.SelectListItem;
import com.spring.project.shared.Sorter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ExcelService excelService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private Validator validator;
    private Department department=new Department();
    @ModelAttribute("department")
    public void setDepartment(Department department){
        this.department=department;
    }
    private DepartmentFilter departmentFilter;
    @ModelAttribute("departmentFilter")
    public void setDepartmentFilter(DepartmentFilter departmentFilter){
        this.departmentFilter=departmentFilter;
    }
    private Pagination pagination=new Pagination();
    @ModelAttribute("pagination")
    public void setPagination(Pagination pagination){
        this.pagination=pagination;
    }
    private Sorter sorter;
    @ModelAttribute("sorter")
    public void setSorter(Sorter sorter){this.sorter=sorter;}
    private List<SelectListItem> entitySelectList=new ArrayList<>();
    private List<SelectListItem> departmentGroupSelectList=new ArrayList<>();

    @GetMapping(value = {"","/","/index"})
    public String index(Model model,
                        HttpServletRequest request){
        populateSelectList(model);
        if(sorter.getSortBy()==null && sorter.getOrderBy()==null){
            sorter=new Sorter("departmentCode",SortingType.ASCENDING);
        }
        var departments=departmentService.departments(departmentFilter,sorter,pagination);
        model.addAttribute("departments",departments);
        model.addAttribute("departmentFilter",departmentFilter);
        model.addAttribute("sorter",sorter);
        model.addAttribute("pagination",pagination);
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
        if(!departmentService.save(department)){
            model.addAttribute("errorsaving","Unable to save department");
            return "/department/edit";
        }
        model.addAttribute("isNew",false);
        return "redirect:edit?id="+department.getDepartmentCode()+"&saveSuccess";
    }

    @GetMapping("/delete")
    public String delete(Model model,
                             @Nullable @RequestParam("id") Integer id){
        department=departmentService.getDepartment(id);
        if(department.getDepartmentCode()==0)
            return "redirect:edit?notfound";
        model.addAttribute("department",department);
        return "/department/delete";
    }
    @PostMapping("/delete")
    public String delete(Model model){
        if(!departmentService.delete(department)){
            model.addAttribute("errordeleting","Unable to delete department");
            return "/department/delete";
        }
        return "redirect:edit?delSuccess";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        var data=departmentService.getTotalRecordsForExport(departmentFilter,sorter,pagination);
        byte[] excelData = excelService.generateExcel(data,"Department");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Departments.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException{
        var data=departmentService.getTotalRecordsForExport(departmentFilter,sorter,pagination);
        byte[] pdfData= pdfService.generatePdf(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "Departments.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }
    public void populateSelectList(Model model){
        entitySelectList.clear();
        for(var entity : entityService.entites()){
            entitySelectList.add(new SelectListItem(
                    entity.getEntityName(),
                    String.valueOf(entity.getEntityId()),
                    entity.getEntityId()==department.getEntity().getEntityId()
            ));
        }
        model.addAttribute("entitySelectList",entitySelectList);
        departmentGroupSelectList.clear();
        for(var group : departmentGroupService.departmentGroups()){
            departmentGroupSelectList.add(new SelectListItem(
                    group.getGroupName(),
                    String.valueOf(group.getGroupId()),
                    group.getGroupId()==department.getDepartmentGroup().getGroupId()
            ));
        }
        model.addAttribute("departmentGroupSelectList",departmentGroupSelectList);
    }
}
