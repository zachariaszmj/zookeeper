package com.example.zookeeperscheduler.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Component
public class BindingResultFactory {
    public BindingResult createForTarget(Object target) {
        return new BeanPropertyBindingResult(target, target.getClass().getName());
    }
}
