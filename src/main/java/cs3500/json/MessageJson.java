package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * represents a message json and its properties
 *
 * @param name The method name
 * @param arguments The method arguments
 */
public record MessageJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") JsonNode arguments
) {
}
