package com.spring.project.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerConfig {
//    @InitBinder
//    void initBinder(final WebDataBinder webDataBinder){
//        webDataBinder.registerCustomEditor(String.class,new StringTrimmerEditor(true));
//    }
}
