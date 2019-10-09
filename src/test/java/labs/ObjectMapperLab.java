package labs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static Utils.FileUtils.TEST_RESOURCE_PATH;
import static jsonMapper.Utils.fileToString;

/* gakshintala created on 10/9/19 */
public class ObjectMapperLab {
    @Test
    void mutateNestedJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        final var jsonNode = objectMapper.readTree(fileToString(TEST_RESOURCE_PATH + "nested-bean.json"));
        final var nested = jsonNode.get("nested");
        final var mutatedJson = ((ObjectNode) jsonNode).set("nested", nested.get("nest1")).toString();
        System.out.println(mutatedJson);
    }

    @Test
    void mutateJsonSetNonExistingKey() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        final var jsonNode = objectMapper.readTree(fileToString(TEST_RESOURCE_PATH + "bean.json"));
        final var nested = jsonNode.get("nested");
        Assertions.assertNull(nested);
    }
}
