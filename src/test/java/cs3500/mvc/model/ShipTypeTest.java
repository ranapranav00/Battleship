package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the ShipType enum
 */
class ShipTypeTest {

  /**
   * Tests the getSize method
   */
  @Test
  void testGetSize() {
    assertEquals(3, ShipType.SUB.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(6, ShipType.CARRIER.getSize());
  }
}