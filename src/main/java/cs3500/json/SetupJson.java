package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents setup json and its properties
 *
 * @param width The width
 * @param height The height
 * @param fleetSpecs The fleet specifications
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpecsJson fleetSpecs
) {
}
