package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a ship json and its properties
 *
 * @param coordJson A coordJson representing the head of the ship
 * @param length The length of the ship
 * @param direction The direction of the ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coordJson,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction
) {
}
