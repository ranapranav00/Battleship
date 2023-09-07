package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents an endgame json and its properties
 *
 * @param result the result of the game
 * @param reason the reason for the result
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason
) {
}
