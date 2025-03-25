package io.limeup.flexbets.sport.config;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SnakeCaseToCamelCaseBinder {

//    @InitBinder
//    public void initBinder(WebDataBinder binder, WebRequest request) {
//        Object target = binder.getTarget();
//        if (target == null) return;
//
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Map<String, Object> transformed = new HashMap<>();
//
//        for (PropertyDescriptor pd : new BeanWrapperImpl(target).getPropertyDescriptors()) {
//            String camelCase = pd.getName();
//            String snakeCase = camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
//
//            if (parameterMap.containsKey(snakeCase)) {
//                String[] values = parameterMap.get(snakeCase);
//                if (values != null && values.length > 0) {
//                    transformed.put(camelCase, values[0]);
//                }
//            }
//        }
//
//        binder.bind(new MutablePropertyValues(transformed));
//    }
}

