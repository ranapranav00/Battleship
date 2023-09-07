package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.json.GameType;
import cs3500.json.JoinJson;
import cs3500.json.JsonUtils;
import org.junit.jupiter.api.Test;



/**
 * represents tests for the JsonUtils class
 */
public class JsonUtilsTest {

  @Test
  void serializeRecordToJson() {
    JoinJson joinRecord = new JoinJson("join", GameType.SINGLE);
    assertEquals("{\"name\":\"join\",\"game-type\":\"SINGLE\"}",
        JsonUtils.serializeRecordToJson(joinRecord).toString());
  }
}