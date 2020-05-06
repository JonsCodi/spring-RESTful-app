package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchHelperComponent {

    private final ObjectMapper mapper;

    public <T> T applyPatch(JsonPatch patch, T objectTarget) {
        try {
            JsonNode patched = patch.apply(convertValueToJsonNode(objectTarget));

            return (T) mapper.treeToValue(patched, objectTarget.getClass());
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public <T> T applyMergePatch(JsonMergePatch jsonMergePatch, T objectTarget) {
        try {
            JsonNode mergeNode = jsonMergePatch.apply(convertValueToJsonNode(objectTarget));

            return (T) mapper.treeToValue(mergeNode, objectTarget.getClass());
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    private <T> JsonNode convertValueToJsonNode(T objectTarget) {
        return mapper.convertValue(objectTarget, JsonNode.class);
    }
}
