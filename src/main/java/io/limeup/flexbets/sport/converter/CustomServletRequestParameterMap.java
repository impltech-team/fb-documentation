package io.limeup.flexbets.sport.converter;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

public class CustomServletRequestParameterMap extends MutablePropertyValues {

    public CustomServletRequestParameterMap(Map<String, String[]> parameterMap) {
        parameterMap.forEach((key, values) -> {
            if (values != null && values.length > 0) {
                this.addPropertyValue(key, values.length > 1 ? values : values[0]);
            }
        });
    }

    public void bind(WebDataBinder binder) {
        binder.bind(this);
    }
}
