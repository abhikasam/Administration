package com.spring.project.shared;

import lombok.Builder;
import lombok.Data;

@Data
public class SelectListItem {
    private String text;
    private String value;
    private boolean selected;
    private String group;
    public SelectListItem(){}

    public SelectListItem(String text,String value){
        this.text=text;
        this.value=value;
    }
    public SelectListItem(String text,String value,boolean selected){
        this.text=text;
        this.value=value;
        this.selected=selected;
    }
    public SelectListItem(String text,String value,boolean selected,String group){
        this.text=text;
        this.value=value;
        this.selected=selected;
        this.group=group;
    }
}
