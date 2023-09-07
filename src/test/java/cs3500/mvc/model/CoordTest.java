package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class to test the Coord class
 */
class CoordTest {
  private Coord coord;
  private Coord coord15;
  private Coord coord51;

  /**
   * Sets values before each test
   */
  @BeforeEach
  void setup() {
    coord = new Coord(0, 0);
    coord15 = new Coord(1, 5);
    coord51 = new Coord(5, 1);
  }

  /**
   * Tests the getX method
   */
  @Test
  void testGetX() {
    assertEquals(0, coord.getX());
    assertEquals(1, coord15.getX());
    assertEquals(5, coord51.getX());
  }

  /**
   * Tests the getY method
   */
  @Test
  void testGetY() {
    assertEquals(0, coord.getY());
    assertEquals(5, coord15.getY());
    assertEquals(1, coord51.getY());
  }

  /**
   * Tests the setState method
   */
  @Test
  void testSetState() {
    assertEquals(State.WATER, coord.getState());
    coord.setState(State.SHIP);
    assertEquals(State.SHIP, coord.getState());
    coord.setState(State.HIT);
    assertEquals(State.HIT, coord.getState());
    coord.setState(State.MISS);
    assertEquals(State.MISS, coord.getState());
  }

  /**
   * Tests the getState method
   */
  @Test
  void testGetState() {
    assertEquals(State.WATER, coord.getState());
    coord.setState(State.SHIP);
    assertEquals(State.SHIP, coord.getState());
    coord.setState(State.HIT);
    assertEquals(State.HIT, coord.getState());
    coord.setState(State.MISS);
    assertEquals(State.MISS, coord.getState());
  }

  /**
   * Tests the getStringRep method
   */
  @Test
  void testGetStringRep() {
    assertEquals("~", coord.getStringRep());
    coord.setState(State.HIT);
    assertEquals("#", coord.getStringRep());
    coord.setState(State.SHIP);
    assertEquals("S", coord.getStringRep());
    coord.setState(State.MISS);
    assertEquals("x", coord.getStringRep());
  }
}