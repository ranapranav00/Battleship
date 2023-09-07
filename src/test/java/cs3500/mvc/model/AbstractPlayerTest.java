package cs3500.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class to Test the AbstractPlayer
 */
class AbstractPlayerTest {
  private Player aiPlayer;
  private Player aiPlayer2;
  private StringBuilder output;
  private Player aiPlayer3;
  private HashMap<ShipType, Integer> specs;
  private ArrayList<Ship> expectedShips;
  private Coord dc4;
  private Coord sc1;
  private Coord sc2;
  private Coord sc3;


  /**
   * Sets values before each test
   */
  @BeforeEach
  void setup() {
    aiPlayer = new AiPlayer("Ai", new Random(1));
    aiPlayer2 = new AiPlayer("Ai", new Random(1));
    output = new StringBuilder();
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUB, 1);

    expectedShips = new ArrayList<>();

    Coord cc1 = new Coord(3, 0);
    Coord cc2 = new Coord(3, 1);
    Coord cc3 = new Coord(3, 2);
    Coord cc4 = new Coord(3, 3);
    Coord cc5 = new Coord(3, 4);
    Coord cc6 = new Coord(3, 5);
    ArrayList<Coord> carrierCoords = new ArrayList<>(Arrays.asList(cc1, cc2, cc3, cc4, cc5, cc6));
    Ship carrier = new Ship(ShipType.CARRIER, carrierCoords);
    expectedShips.add(carrier);

    Coord bc1 = new Coord(5, 0);
    Coord bc2 = new Coord(5, 1);
    Coord bc3 = new Coord(5, 2);
    Coord bc4 = new Coord(5, 3);
    Coord bc5 = new Coord(5, 4);
    ArrayList<Coord> battleShipCoords = new ArrayList<>(Arrays.asList(bc1, bc2, bc3, bc4, bc5));
    Ship battleShip = new Ship(ShipType.BATTLESHIP, battleShipCoords);
    expectedShips.add(battleShip);

    Coord dc1 = new Coord(2, 1);
    Coord dc2 = new Coord(2, 2);
    Coord dc3 = new Coord(2, 3);
    dc4 = new Coord(2, 4);
    ArrayList<Coord> destroyerCoords = new ArrayList<>(Arrays.asList(dc1, dc2, dc3, dc4));
    Ship destroyer = new Ship(ShipType.DESTROYER, destroyerCoords);
    expectedShips.add(destroyer);

    sc1 = new Coord(1, 1);
    sc2 = new Coord(1, 2);
    sc3 = new Coord(1, 3);
    ArrayList<Coord> subCoords = new ArrayList<>(Arrays.asList(sc1, sc2, sc3));
    Ship sub = new Ship(ShipType.SUB, subCoords);
    expectedShips.add(sub);
  }

  /**
   * Tests the name Method
   */
  @Test
  void testName() {
    assertEquals("Ai", aiPlayer.name());
  }

  /**
   * Tests the setup method
   */
  @Test
  void testSetup() {
    List<Ship> actualShips = aiPlayer.setup(6, 6, specs);
    for (int i = 0; i < actualShips.size(); i++) {
      assertEquals(expectedShips.get(i), actualShips.get(i));
    }


  }

  /**
   * Tests the reportDamage method
   */
  @Test
  void testReportDamage() {
    aiPlayer.setup(6, 6, specs);
    aiPlayer2.setup(6, 6, specs);
    List<Coord> actualVolley1Hits = aiPlayer.reportDamage(aiPlayer2.takeShots());
    for (Coord c : actualVolley1Hits) {
      System.out.println(c.getX() + " " + c.getY());
    }
    List<Coord> expectedVolley1Hits = new ArrayList<>(Arrays.asList(dc4, sc1, sc3));
    assertEquals(expectedVolley1Hits.size(), actualVolley1Hits.size());
    for (int i = 0; i < actualVolley1Hits.size(); i++) {
      assertEquals(expectedVolley1Hits.get(i).getX(), actualVolley1Hits.get(i).getX());
      assertEquals(expectedVolley1Hits.get(i).getY(), actualVolley1Hits.get(i).getY());
    }
  }

  /**
   * Tests the SuccessfulHits Method
   */
  @Test
  void testSuccessfulHits() {
    //aiPlayer.successfulHits(List.of(sc1, sc2, sc3));
  }

  /**
   * Tests the EndGame method
   */
  @Test
  void testEndGame() {
    aiPlayer3.endGame(GameResult.WIN, "you win!");
    assertEquals("You WIN you win!", output.toString());
  }
}