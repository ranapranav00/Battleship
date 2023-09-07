package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.json.Direction;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the shipTest class
 */
class ShipTest {
  ArrayList<Coord> shipCoords;
  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Ship ship;

  /**
   * Sets values before each test
   */
  @BeforeEach
  void setup() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 1);
    c3 = new Coord(0, 2);
    c4 = new Coord(1, 2);
    shipCoords = new ArrayList<>(Arrays.asList(c1, c2, c3));
    ship = new Ship(ShipType.SUB, shipCoords);
  }

  /**
   * Tests the getCoords method
   */
  @Test
  void testGetCoords() {
    for (int i = 0; i < shipCoords.size(); i++) {
      assertEquals(shipCoords.get(i), ship.getCoords().get(i));
    }
    assertArrayEquals(shipCoords.toArray(), ship.getCoords().toArray());
  }

  /**
   * Tests the overriden Equals method
   */
  @Test
  void testEquals() {
    assertNotEquals(ship, new Coord(1, 1));
    assertNotEquals(new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c1, c2))),
        new Ship(ShipType.CARRIER, new ArrayList<>(Arrays.asList(c1, c2))));
    assertNotEquals(new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c3, c1))),
        new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c4, c1))));
    assertNotEquals(new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c2, c4))),
        new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c3, c4))));
    assertEquals(new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c1, c2))),
        new Ship(ShipType.SUB, new ArrayList<>(Arrays.asList(c1, c2))));
  }

  /**
   * tests the getDirection method
   */
  @Test
  void testGetDirection() {
    assertEquals(Direction.VERTICAL, ship.getDirection());
  }
}