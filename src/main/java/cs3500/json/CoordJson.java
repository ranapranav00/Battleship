package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a coordinate json and its properties
 *
 * @param x the x coord
 * @param y the y coord
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y
) {
}
