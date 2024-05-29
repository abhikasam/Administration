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
import java.util.Comparator;
import java.util.Map;
import java.util.regex.Pattern;

public class DisplayNameTagProcessor extends AbstractAttributeTagProcessor {
    private static final String attr_name="field";
    private static final int precendence=10000;
    public DisplayNameTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false,
                attr_name, true, precendence, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        try{
            StandardExpressionParser expressionParser=new StandardExpressionParser();
            String fieldExpression="";
            if(attributeValue.startsWith("${") && attributeValue.endsWith("}")){
                fieldExpression=attributeValue.substring(2,attributeValue.length()-1);
                int lastDotIndex=fieldExpression.lastIndexOf('.');
                if(lastDotIndex!=-1){
                    String field=fieldExpression.substring(lastDotIndex+1);
                    String[] classNames=fieldExpression.substring(0,lastDotIndex).split(Pattern.quote("."));
                    Class<?> cls=context.getVariable(classNames[0]).getClass();
                    for(int i=1;i< classNames.length;i++){
                        cls=cls.getDeclaredField(classNames[i]).getType();
                    }
                    String displayName=getDisplayName(cls,field);
                    String text=tag.getAttributeValue("th","text");
                    if(text!=null && text.length()>0){
                        displayName=text.replaceAll("\\{0}",displayName);
                    }
                    structureHandler.setBody(displayName,false);
                }
                else{
                    structureHandler.setBody("",false);
                }
            }
            else{
                structureHandler.setBody("",false);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            structureHandler.setBody("",false);
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
