package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * represents a coordinates json and its properties
 *
 * @param shots The
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<CoordJson> shots
) {
}
