package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * A class to Test the AiPlayer
 */
class AiPlayerTest {

  /**
   * Tests the takeShots method
   */
  @Test
  void testTakeShots() {
    final Player ai = new AiPlayer("Ai", new Random(1));
    HashMap<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUB, 1);
    ai.setup(6, 6, specs);
    Coord c1 = new Coord(2, 4);
    Coord c2 = new Coord(4, 4);
    Coord c3 = new Coord(1, 1);
    Coord c4 = new Coord(1, 3);
    ArrayList<Coord> expectedShots = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    List<Coord> actualShots = ai.takeShots();
    assertEquals(expectedShots.size(), actualShots.size());
    for (int i = 0; i < actualShots.size(); i++) {
      assertEquals(expectedShots.get(i).getX(), actualShots.get(i).getX());
      assertEquals(expectedShots.get(i).getY(), actualShots.get(i).getY());
    }
  }
}