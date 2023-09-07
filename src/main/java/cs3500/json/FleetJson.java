package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * represents a fleet json and its properties
 *
 * @param ships the ships in the fleet
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> ships
) {
}