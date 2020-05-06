package org.restful.soccer_league.domains.utils.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchHelperComponent {

    private final ObjectMapper mapper;

    public <T> T applyPatch(JsonPatch patch, T objectTarget) {
        JsonNode jsonNode = mapper.convertValue(objectTarget, JsonNode.class);
        try {
            JsonNode patched = patch.apply(jsonNode);

            return (T) mapper.treeToValue(patched, objectTarget.getClass());
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();

            throw new RuntimeException();
        }

    }

}
