package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a fleet specifications json and its properties
 *
 * @param numCarrier the number of carriers in the fleet
 * @param numBattleships the number of battleships in the fleet
 * @param numDestroyers the number of destroyers in the fleet
 * @param numSubmarines the number of submarines in the fleet
 */
public record FleetSpecsJson(
    @JsonProperty("CARRIER") int numCarrier,
    @JsonProperty("BATTLESHIP") int numBattleships,
    @JsonProperty("DESTROYER") int numDestroyers,
    @JsonProperty("SUBMARINE") int numSubmarines
) {
}
