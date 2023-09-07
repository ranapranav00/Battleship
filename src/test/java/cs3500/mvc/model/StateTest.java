package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the State enum
 */
class StateTest {

  /**
   * Tests the getStringRep method
   */
  @Test
  void testGetStringRep() {
    assertEquals("S", State.SHIP.getStringRep());
    assertEquals("#", State.HIT.getStringRep());
    assertEquals("~", State.WATER.getStringRep());
    assertEquals("x", State.MISS.getStringRep());
  }
}