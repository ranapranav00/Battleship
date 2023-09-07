package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.json.Message;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the Message enum
 */
public class MessageTest {

  @Test
  void testGetMethodName() {
    assertEquals("join", Message.JOIN.getMethodName());
    assertEquals("setup", Message.SETUP.getMethodName());
    assertEquals("take-shots", Message.TAKESHOTS.getMethodName());
    assertEquals("report-damage", Message.REPORTDAMAGE.getMethodName());
    assertEquals("successful-hits", Message.SUCCESSFULHITS.getMethodName());
    assertEquals("end-game", Message.ENDGAME.getMethodName());
  }
}