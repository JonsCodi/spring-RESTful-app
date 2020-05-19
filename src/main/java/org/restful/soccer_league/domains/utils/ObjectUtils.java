package org.restful.soccer_league.domains.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public final class ObjectUtils {

    private static final String DELIMITATOR = ",";

    public static List<Object> getFieldsFromModelObject(String fields, Object modelObject) {
        List<Object> correctFields = new ArrayList<>();

        for(String field : fields.split(DELIMITATOR)) {
            Object fieldValueFromModelObject = new Object();

            fieldValueFromModelObject = getFieldName(modelObject, field, fieldValueFromModelObject);

            if(Objects.nonNull(fieldValueFromModelObject)) {
                correctFields.add(field);
            }
        }

        return correctFields;
    }

    private static Object getFieldName(Object modelObject, String field, Object fieldValueFromModelObject) {
        if(modelObject instanceof ArrayList) {
            ArrayList list = (ArrayList) modelObject;

            fieldValueFromModelObject = ((LinkedHashMap) list.get(0)).get(field);
        }else if(modelObject instanceof LinkedHashMap) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) modelObject;

            fieldValueFromModelObject = linkedHashMap.get(field);
        }

        return fieldValueFromModelObject;
    }

    //TODO: Quando entrar na internacionalização: A camada de validação que deve cuidar de possíveis erros de Fields Inválidos.
    public static void throwIfOnlyHaveInvalidFields(String fields, Object modelObject) {
        if(modelObject instanceof ArrayList) {
            throwForArrayListObject(fields, (ArrayList) modelObject);
        }else if(modelObject instanceof LinkedHashMap) {
            throwForLinkedHashMapObject(fields, (LinkedHashMap) modelObject);
        }
    }

    private static void throwForLinkedHashMapObject(String fields, LinkedHashMap modelObject) {
        LinkedHashMap linkedHashMap = modelObject;

        if(linkedHashMap.isEmpty()) {
            throw new IllegalArgumentException("Have some invalid Fields: ".concat(fields));
        }
    }

    private static void throwForArrayListObject(String fields, ArrayList modelObject) {
        ArrayList list = modelObject;

        if(((LinkedHashMap) list.get(0)).isEmpty()) {
            throw new IllegalArgumentException("Have some invalid Fields: ".concat(fields));
        }
    }
}
