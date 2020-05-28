package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.restful.soccer_league.domains.utils.ObjectUtils;
import org.restful.soccer_league.domains.utils.api.web.v1.response.ResponseSuccessBody;
import org.restful.soccer_league.domains.utils.enums.FiltersEnum;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ResponseEntityComponent {

    private final BeanPropertyFilterComponent beanPropertyFilterComponent;

    @Setter
    private FiltersEnum[] jsonFilters;

    private static final String DELIMITATOR = ",";

    public ResponseEntity<ResponseSuccessBody> returnPartialContent(String fields, Object object, Links links, Object page) {
        fields = fields.replace(" ", "");

        beanPropertyFilterComponent.createFilter(SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(DELIMITATOR)),
                jsonFilters);
        Object modelObject = beanPropertyFilterComponent.convertToObject(object, Object.class);

        ObjectUtils.throwUnknownFieldException(fields, modelObject);

        List<Object> correctFields = ObjectUtils.getFieldsFromModelObject(fields, modelObject);

        ResponseSuccessBody successBody = new ResponseSuccessBody(
                links, modelObject, correctFields, page
        );

        return new ResponseEntity(successBody, HttpStatus.PARTIAL_CONTENT);
    }

    public ResponseEntity<ResponseSuccessBody> returnAllContent(Object object, Links links, Object page) {
        beanPropertyFilterComponent.createFilter(SimpleBeanPropertyFilter.serializeAll(), jsonFilters);

        ResponseSuccessBody successBody = new ResponseSuccessBody(
                links, object, null, page
        );

        return ResponseEntity.ok(successBody);
    }
}
