package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests the ManualPlayer class
 */
class ManualPlayerTest {

  /**
   * Tests the takeShots method
   */
  @Test
  void testTakeShots() {
    Readable input = new StringReader("0 5\n 1 4\n 2 3\n 3 2\n");
    Appendable output = new StringBuilder();
    final Player user = new ManualPlayer("User", new Random(1), input, output);
    HashMap<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUB, 1);
    user.setup(6, 6, specs);
    ArrayList<Coord> coords = (ArrayList<Coord>) user.takeShots();
    assertEquals(0, coords.get(0).getX());
    assertEquals(5, coords.get(0).getY());
    assertEquals(1, coords.get(1).getX());
    assertEquals(4, coords.get(1).getY());
    assertEquals(2, coords.get(2).getX());
    assertEquals(3, coords.get(2).getY());
    assertEquals(3, coords.get(3).getX());
    assertEquals(2, coords.get(3).getY());
  }
}