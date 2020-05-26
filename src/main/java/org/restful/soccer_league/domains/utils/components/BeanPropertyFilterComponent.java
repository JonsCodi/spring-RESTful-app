package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Lazy
public class BeanPropertyFilterComponent {

    private ObjectMapper objectMapper;
    private SimpleFilterProvider filterProvider;

    public BeanPropertyFilterComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.filterProvider = new SimpleFilterProvider();
    }

    public void createFilter(SimpleBeanPropertyFilter simpleBeanPropertyFilter, FiltersEnum[] filtersEnums) {
        Arrays.asList(filtersEnums).forEach(filter -> {
            filterProvider.addFilter(filter.getFilter(), simpleBeanPropertyFilter);
        });

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
