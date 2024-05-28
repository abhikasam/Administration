package com.spring.project.dialect;

import com.spring.project.tagprocessor.DisplayNameTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class DisplayNameDialect extends AbstractProcessorDialect {
    public DisplayNameDialect(){
        super("Display Name Dialect","display",10000);
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        Set<IProcessor> processors=new HashSet<>();
        processors.add(new DisplayNameTagProcessor(dialectPrefix));
        return processors;
    }
}
