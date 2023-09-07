package cs3500.mvc.controller;

import cs3500.mvc.model.GameModel;
import cs3500.mvc.model.Player;
import cs3500.mvc.model.ShipType;
import cs3500.mvc.view.View;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Controller Object used for local battleship games
 */
public class LocalController implements Controller {
  private int height;
  private int width;
  private int maxShips;
  private final View view;
  private final GameModel gameModel;
  private final Map<ShipType, Integer> shipCount;

  /**
   * A constructor for the controller
   *
   * @param player1 Player 1
   * @param player2 Player 2
   */
  public LocalController(Player player1, Player player2) {
    this.shipCount = new HashMap<>();
    this.gameModel = new GameModel(player1, player2);
    this.view = new View(new InputStreamReader(System.in), System.out);
  }

  /**
   * A constructor for the controller to be used for testing
   *
   * @param p1     Player 1
   * @param p2     Player 2
   * @param input  A readable object
   * @param output An appendable object
   */
  public LocalController(Player p1, Player p2, Readable input, Appendable output) {
    this.shipCount = new HashMap<>();
    this.gameModel = new GameModel(p1, p2);
    this.view = new View(input, output);
  }

  /**
   * Runs the game
   * Prompts user for input, initializes game state, executes gameLoop, and ends game
   */
  public void run() {
    view.write("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """);
    getBoardDimensionsFromUser();
    view.write("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size\s""" + maxShips + ".\n"
        + "------------------------------------------------------\n");
    getFleetDetailsFromUser();
    gameModel.start();
    String p1BoardRep = gameModel.getBoardAsString(false);
    String p2BoardRep = gameModel.getBoardAsString(true);
    view.displayBoards(p1BoardRep, p2BoardRep);
    while (gameModel.getPlayer1Ships().size() != 0 && gameModel.getPlayer2Ships().size() != 0) {
      view.write("Please enter " + gameModel.getPlayer1Ships().size()
          + " shots; Remember to put each coordinate on its own line.\n");
      gameModel.gameLoop();

      view.displayBoards(gameModel.getBoardAsString(false),
          gameModel.getBoardAsString(true));
    }
    view.write(gameModel.endGame());
  }

  /**
   * Interprets user input to get board dimensions
   */
  private void getBoardDimensionsFromUser() {
    try {
      height = Integer.parseInt(view.getNext());
      width = Integer.parseInt(view.getNext());
      view.burn();
      if (height < 6 || height > 15 || width < 6 || width > 15) {
        dimensionError();
      }
    } catch (Exception e) {
      dimensionError();
    }
    gameModel.setHeight(height);
    gameModel.setWidth(width);
    maxShips = Math.min(height, width);
  }

  /**
   * In any case where invalid dimensions were given, prompts the user to try again
   */
  private void dimensionError() {
    view.burn();
    view.write("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """);
    getBoardDimensionsFromUser();
  }

  /**
   * Interprets user input to get Fleet Details
   */
  private void getFleetDetailsFromUser() {
    try {
      int numCarriers = Integer.parseInt(view.getNext());
      int numBattleShips = Integer.parseInt(view.getNext());
      int numDestroyers = Integer.parseInt(view.getNext());
      int numSubs = Integer.parseInt(view.getNext());
      if (numCarriers + numBattleShips + numDestroyers + numSubs > maxShips
          || numCarriers == 0 || numBattleShips == 0 || numDestroyers == 0 || numSubs == 0) {
        fleetDetailsError();
      } else {
        shipCount.put(ShipType.CARRIER, numCarriers);
        shipCount.put(ShipType.BATTLESHIP, numBattleShips);
        shipCount.put(ShipType.DESTROYER, numDestroyers);
        shipCount.put(ShipType.SUB, numSubs);
        gameModel.setShipCount(shipCount);
      }
    } catch (Exception e) {
      fleetDetailsError();
    }
  }

  /**
   * In any case where invalid Fleet Details were given, prompts the user to try again
   */
  private void fleetDetailsError() {
    view.burn();
    view.write("""
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, you must have at least 1 of each ShipType and your fleet may not exceed size\s"""
        + maxShips + ".\n" + "------------------------------------------------------\n");
    getFleetDetailsFromUser();
  }
}