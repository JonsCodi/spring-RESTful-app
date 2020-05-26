package org.restful.soccer_league.domains.utils;

import org.restful.soccer_league.domains.utils.exceptions.UnknownFieldException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public final class ObjectUtils {

    public static List<Object> getFieldsFromModelObject(String fields, Object modelObject) {
        List<Object> correctFields = new ArrayList<>();

        for(String field : fields.split(",")) {
            Object fieldNameFromModelObject = getFieldName(modelObject, field);

            if(Objects.nonNull(fieldNameFromModelObject)) {
                correctFields.add(field);
            }
        }

        return correctFields;
    }

    private static Object getFieldName(Object modelObject, String field) {
        Object fieldNameFromModelObject = null;

        if(modelObject instanceof ArrayList) {
            ArrayList list = (ArrayList) modelObject;

            fieldNameFromModelObject = ((LinkedHashMap) list.get(0)).get(field);
        }else if(modelObject instanceof LinkedHashMap) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) modelObject;

            fieldNameFromModelObject = linkedHashMap.get(field);
        }

        return fieldNameFromModelObject;
    }

    //TODO: Quando entrar na internacionalização: A camada de validação que deve cuidar de possíveis erros de Fields Inválidos.
    public static void throwUnknownFieldException(String fields, Object modelObject) {
        if(modelObject instanceof ArrayList) {
            throwForArrayListObject(fields, (ArrayList) modelObject);
        }else if(modelObject instanceof LinkedHashMap) {
            throwForLinkedHashMapObject(fields, (LinkedHashMap) modelObject);
        }
    }

    private static void throwForLinkedHashMapObject(String fields, LinkedHashMap modelObject) {
        LinkedHashMap linkedHashMap = modelObject;

        if(linkedHashMap.isEmpty()) {
            throw new UnknownFieldException("You have some invalid Field(s).", fields);
        }
    }

    private static void throwForArrayListObject(String fields, ArrayList modelObject) {
        ArrayList list = modelObject;

        if(((LinkedHashMap) list.get(0)).isEmpty()) {
            throw new UnknownFieldException("You have some invalid Field(s).", fields);
        }
    }
}
