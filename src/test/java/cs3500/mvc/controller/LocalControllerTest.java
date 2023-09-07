package cs3500.mvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.mvc.model.AiPlayer;
import cs3500.mvc.model.Player;
import cs3500.mvc.model.ShipType;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class to test the Controller
 */
class LocalControllerTest {

  private Player p1;
  private Player p2;
  private Appendable output;
  private String msg;
  private Map<ShipType, Integer> specs;

  /**
   * Sets values to some objects before each test
   */
  @BeforeEach
  void setup() {
    p1 = new AiPlayer("p1", new Random(1));
    p2 = new AiPlayer("p2", new Random(1));
    output = new StringBuilder();
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUB, 1);

    msg = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size 6.
        ------------------------------------------------------
        Opponent Board Data:
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s

        Your Board:
        ~ ~ ~ S ~ S\s
        ~ S S S ~ S\s
        ~ S S S ~ S\s
        ~ S S S ~ S\s
        ~ ~ S S ~ S\s
        ~ ~ ~ S ~ ~\s
        Please enter 4 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        ~ ~ ~ ~ ~ ~\s
        ~ # ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ # ~ ~ ~ ~\s
        ~ ~ # ~ x ~\s
        ~ ~ ~ ~ ~ ~\s

        Your Board:
        ~ ~ ~ S ~ S\s
        ~ # S S ~ S\s
        ~ S S S ~ S\s
        ~ # S S ~ S\s
        ~ ~ # S x S\s
        ~ ~ ~ S ~ ~\s
        Please enter 4 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        ~ ~ x ~ ~ ~\s
        ~ # ~ ~ ~ ~\s
        ~ ~ # ~ ~ ~\s
        ~ # ~ ~ ~ ~\s
        x ~ # ~ x ~\s
        x ~ ~ ~ ~ ~\s

        Your Board:
        ~ ~ x S ~ S\s
        ~ # S S ~ S\s
        ~ S # S ~ S\s
        ~ # S S ~ S\s
        x ~ # S x S\s
        x ~ ~ S ~ ~\s
        Please enter 4 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        ~ ~ x ~ ~ ~\s
        ~ # ~ ~ ~ ~\s
        ~ ~ # # ~ ~\s
        ~ # ~ ~ ~ ~\s
        x x # ~ x ~\s
        x ~ x ~ x ~\s

        Your Board:
        ~ ~ x S ~ S\s
        ~ # S S ~ S\s
        ~ S # # ~ S\s
        ~ # S S ~ S\s
        x x # S x S\s
        x ~ x S x ~\s
        Please enter 4 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x ~ x # ~ ~\s
        ~ # ~ ~ ~ ~\s
        ~ # # # ~ ~\s
        ~ # ~ ~ ~ #\s
        x x # ~ x ~\s
        x ~ x ~ x ~\s

        Your Board:
        x ~ x # ~ S\s
        ~ # S S ~ S\s
        ~ # # # ~ S\s
        ~ # S S ~ #\s
        x x # S x S\s
        x ~ x S x ~\s
        Please enter 3 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x ~ x # ~ ~\s
        x # ~ ~ ~ ~\s
        ~ # # # ~ ~\s
        ~ # # ~ x #\s
        x x # ~ x ~\s
        x ~ x ~ x ~\s

        Your Board:
        x ~ x # ~ S\s
        x # S S ~ S\s
        ~ # # # ~ S\s
        ~ # # S x #\s
        x x # S x S\s
        x ~ x S x ~\s
        Please enter 3 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x ~ x # x ~\s
        x # # ~ ~ ~\s
        ~ # # # ~ #\s
        ~ # # ~ x #\s
        x x # ~ x ~\s
        x ~ x ~ x ~\s

        Your Board:
        x ~ x # x S\s
        x # # S ~ S\s
        ~ # # # ~ #\s
        ~ # # S x #\s
        x x # S x S\s
        x ~ x S x ~\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x ~ x # x ~\s
        x # # ~ ~ ~\s
        ~ # # # ~ #\s
        x # # ~ x #\s
        x x # ~ x ~\s
        x ~ x # x ~\s

        Your Board:
        x ~ x # x S\s
        x # # S ~ S\s
        ~ # # # ~ #\s
        x # # S x #\s
        x x # S x S\s
        x ~ x # x ~\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x ~ x # x #\s
        x # # ~ ~ ~\s
        ~ # # # ~ #\s
        x # # ~ x #\s
        x x # ~ x ~\s
        x ~ x # x x\s

        Your Board:
        x ~ x # x #\s
        x # # S ~ S\s
        ~ # # # ~ #\s
        x # # S x #\s
        x x # S x S\s
        x ~ x # x x\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x x x # x #\s
        x # # # ~ ~\s
        ~ # # # ~ #\s
        x # # ~ x #\s
        x x # ~ x ~\s
        x ~ x # x x\s

        Your Board:
        x x x # x #\s
        x # # # ~ S\s
        ~ # # # ~ #\s
        x # # S x #\s
        x x # S x S\s
        x ~ x # x x\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x x x # x #\s
        x # # # x ~\s
        ~ # # # ~ #\s
        x # # ~ x #\s
        x x # # x ~\s
        x ~ x # x x\s

        Your Board:
        x x x # x #\s
        x # # # x S\s
        ~ # # # ~ #\s
        x # # S x #\s
        x x # # x S\s
        x ~ x # x x\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x x x # x #\s
        x # # # x ~\s
        ~ # # # x #\s
        x # # ~ x #\s
        x x # # x #\s
        x ~ x # x x\s

        Your Board:
        x x x # x #\s
        x # # # x S\s
        ~ # # # x #\s
        x # # S x #\s
        x x # # x #\s
        x ~ x # x x\s
        Please enter 2 shots; Remember to put each coordinate on its own line.
        Opponent Board Data:
        x x x # x #\s
        x # # # x #\s
        ~ # # # x #\s
        x # # # x #\s
        x x # # x #\s
        x ~ x # x x\s

        Your Board:
        x x x # x #\s
        x # # # x #\s
        ~ # # # x #\s
        x # # # x #\s
        x x # # x #\s
        x ~ x # x x\s
        You tied!""";
  }

  /**
   * Tests the run method
   */
  @Test
  void testRun() {
    Readable inputs =
        new StringReader(
            "1 1\n\n16 16\n\n6 16\n\n16 6\n\na a\n6 a\na 6\n1 1 1\n\n6 6\n0 1 1 1\n"
                + "1 0 1 1\n1 1 0 1\n1 1 1 0\n5 5 5 5\n0 20 1 1\n20 0 1 1\n20 20 0 1\n20 20 20 0\n"
                + "0 0 0 0\n1 1 1 1");
    LocalController controller = new LocalController(p1, p2, inputs, output);
    controller.run();
    assertEquals(msg, output.toString());
    setup();
  }
}