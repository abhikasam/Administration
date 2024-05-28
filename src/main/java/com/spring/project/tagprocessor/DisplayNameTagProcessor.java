package com.spring.project.tagprocessor;

import com.spring.project.annotations.DisplayName;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.standard.util.StandardExpressionUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.Map;

public class DisplayNameTagProcessor extends AbstractAttributeTagProcessor {
    private static final String entity_path="package";
    private static final String attr_name="field";
    private static final int precendence=10000;
    public DisplayNameTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false,
                attr_name, true, precendence, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        try{
            String mainClassName = ManagementFactory.getRuntimeMXBean().getSystemProperties().get("sun.java.command");
            String mainClassPackageName = mainClassName.substring(0, mainClassName.lastIndexOf('.'));
            String entityPath=tag.getAttributeValue(getDialectPrefix(),entity_path);
            String entityPackagePath=mainClassPackageName+"."+entityPath;
            Class<?> cls=Class.forName(entityPackagePath);
            String displayName=getDisplayName(cls,attributeValue);
            String text=tag.getAttributeValue("th","text");
            if(text!=null && text.length()>0){
                displayName=text.replaceAll("\\{0}",displayName);
            }
            structureHandler.setBody(displayName,false);
        }
        catch (Exception ex){
            ex.printStackTrace();
            structureHandler.setBody(attributeValue,false);
        }
    }

    String getDisplayName(Class<?> cls,String fieldName){
        try{
            Field field=cls.getDeclaredField(fieldName);
            if(field.isAnnotationPresent(DisplayName.class)){
                return field.getAnnotation(DisplayName.class).value();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return fieldName;
    }
}
