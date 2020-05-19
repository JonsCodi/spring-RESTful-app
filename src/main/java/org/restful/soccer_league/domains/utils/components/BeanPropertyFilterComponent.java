package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BeanPropertyFilterComponent {

    private ObjectMapper objectMapper;
    private SimpleFilterProvider filterProvider;

    public BeanPropertyFilterComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.filterProvider = new SimpleFilterProvider();
    }

    public void createFilter(String modelFilter, SimpleBeanPropertyFilter simpleBeanPropertyFilter) {
        filterProvider.addFilter(modelFilter, simpleBeanPropertyFilter);

        objectMapper.setFilterProvider(filterProvider);
    }

    public Object convertToObject(Object object, Class<?> clazz) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(object), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
