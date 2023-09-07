package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a join json and its properties
 *
 * @param name the player's name
 * @param gameType the game type
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}