package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import lombok.RequiredArgsConstructor;
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

    private static final String DELIMITATOR = ",";

    public ResponseEntity<ResponseSuccessBody> returnPartialContent(FiltersEnum jsonFilter, String fields, Object object, Links links, Object page) {
        fields = fields.replace(" ", "");

        beanPropertyFilterComponent.createFilter(jsonFilter.getFilter(), SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(DELIMITATOR)));
        Object modelObject = beanPropertyFilterComponent.convertToObject(object, Object.class);

        ObjectUtils.throwIfOnlyHaveInvalidFields(fields, modelObject);
        List<Object> correctFields = ObjectUtils.getFieldsFromModelObject(fields, modelObject);

        ResponseSuccessBody successBody = new ResponseSuccessBody(
                links, modelObject, correctFields, page
        );

        return new ResponseEntity(successBody, HttpStatus.PARTIAL_CONTENT);
    }

    public ResponseEntity<ResponseSuccessBody> returnAllContent(FiltersEnum jsonFilter, Object object, Links links, Object page) {
        beanPropertyFilterComponent.createFilter(jsonFilter.getFilter(), SimpleBeanPropertyFilter.serializeAll());

        ResponseSuccessBody successBody = new ResponseSuccessBody(
                links, object, null, page
        );

        return ResponseEntity.ok(successBody);
    }
}
