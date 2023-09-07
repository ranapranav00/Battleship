package cs3500.mvc.model;

import cs3500.mvc.view.View;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an Abstract Player
 */
public abstract class AbstractPlayer implements Player {
  protected int height;
  protected int width;

  protected Coord[][] board;
  Coord[][] opponentBoard;
  protected ArrayList<Coord> possibleShots = new ArrayList<>();
  protected ArrayList<Ship> ships = new ArrayList<>();
  protected ArrayList<Coord> shotsOnShips = new ArrayList<>();
  protected String name;
  protected Random rand;
  protected Algorithm setupAlg;
  protected View view;
  protected List<Coord> currentSalvo = new ArrayList<>();
  protected Mode currentMode = Mode.HUNT;

  /**
   * A constructor for an abstract player
   *
   * @param name The name of the player
   * @param rand A random object
   */

  public AbstractPlayer(String name, Random rand) {
    this.name = name;
    this.rand = rand;
    this.view = new View(new InputStreamReader(System.in), System.out);
  }

  /**
   * Gets the name of the player
   *
   * @return The name of the player
   */
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.height = height;
    this.width = width;
    this.board = createBoard();
    this.opponentBoard = createBoard();
    this.setupAlg = new Algorithm(board);
    for (Coord[] coords : board) {
      possibleShots.addAll(List.of(coords));
    }
    int numCarriers = specifications.get(ShipType.CARRIER);
    int numBattleShips = specifications.get(ShipType.BATTLESHIP);
    int numDestroyers = specifications.get(ShipType.DESTROYER);
    int numSubs = specifications.get(ShipType.SUB);
    ships.addAll(generateShips(ShipType.CARRIER, numCarriers));
    ships.addAll(generateShips(ShipType.BATTLESHIP, numBattleShips));
    ships.addAll(generateShips(ShipType.DESTROYER, numDestroyers));
    ships.addAll(generateShips(ShipType.SUB, numSubs));
    return ships;
  }

  /**
   * Generates a list of ships of a given type, properly placed on the board
   *
   * @param type     The type of ship
   * @param numShips The number of this shipType
   * @return A list of ships of the given type
   */
  private ArrayList<Ship> generateShips(ShipType type, int numShips) {
    ArrayList<Ship> ships = new ArrayList<>();
    for (int i = 0; i < numShips; i++) {
      ArrayList<ArrayList<Coord>> placements = setupAlg.getPossiblePlacements(type.getSize());
      int index = rand.nextInt(placements.size());
      ArrayList<Coord> shipCoords = placements.get(index);
      for (Coord c : shipCoords) {
        c.setState(State.SHIP);
      }
      Ship ship = new Ship(type, shipCoords);
      ships.add(ship);
    }
    return ships;
  }

  /**
   * Creates a board of coordinates
   *
   * @return The board
   */
  private Coord[][] createBoard() {
    Coord[][] board = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Coord(j, i);
      }
    }
    return board;
  }

//  /**
//   * Gets the possible placements of a ship of the given size
//   *
//   * @param size The size of the ship
//   * @return The possible list of placements on the board
//   */
//  private ArrayList<ArrayList<Coord>> getPossiblePlacements(int size) {
//    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
//    listOfPlacements.addAll(getHorizontalPlacements(size, board));
//    listOfPlacements.addAll(getVerticalPlacements(size, board));
//    return listOfPlacements;
//  }
//
//  /**
//   * Gets the possible horizontal placements on the board
//   *
//   * @param size The size of the ship
//   * @return All possible horizontal placements for a ship of this size on the board
//   */
//
//  private ArrayList<ArrayList<Coord>> getHorizontalPlacements(int size, Coord[][] board) {
//    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
//    for (int i = 0; i < height; i++) {
//      for (int j = 0; j < width; j++) {
//        ArrayList<Coord> placement = new ArrayList<>();
//        for (int k = 0; k < size; k++) {
//          if (j + k >= width) {
//            break;
//          }
//          Coord coord = board[i][j + k];
//          if (coord.getState().equals(State.WATER)) {
//            placement.add(coord);
//          }
//        }
//        if (placement.size() == size) {
//          listOfPlacements.add(placement);
//        }
//      }
//    }
//    return listOfPlacements;
//  }
//
//  /**
//   * Gets all the vertical placements of a ship of the given size on the board
//   *
//   * @param size The size of the ship
//   * @return All possible vertical placements of this ship size on the board
//   */
//  private ArrayList<ArrayList<Coord>> getVerticalPlacements(int size, Coord[][] board) {
//    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
//    for (int i = 0; i < width; i++) {
//      for (int j = 0; j < height; j++) {
//        ArrayList<Coord> placement = new ArrayList<>();
//        for (int k = 0; k < size; k++) {
//          if (j + k >= height) {
//            break;
//          }
//          Coord coord = board[j + k][i];
//          if (coord.getState().equals(State.WATER)) {
//            placement.add(coord);
//          }
//        }
//        if (placement.size() == size) {
//          listOfPlacements.add(placement);
//        }
//      }
//    }
//    return listOfPlacements;
//  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   *         on this board
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> shots = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      if (board[coord.getY()][coord.getX()].getState().equals(State.SHIP)) {
        Coord c = new Coord(coord.getX(), coord.getY());
        c.setState(State.HIT);
        shots.add(c);
        shotsOnShips.add(c);
      }
    }
    checkShipHealth();
    return shots;
  }

  private void checkShipHealth() {
    ArrayList<Ship> shipDupe = new ArrayList<>(ships);
    for (Ship s : shipDupe) {
      boolean sunk = true;
      for (Coord c : s.getCoords()) {
        Coord coord = new Coord(c.getX(), c.getY());
        coord.setState(State.HIT);
        if (!shotsOnShips.contains(coord)) {
          sunk = false;
          break;
        }
      }
      if (sunk) {
        ships.remove(s);
      }
    }
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      opponentBoard[c.getY()][c.getX()].setState(State.HIT);
    }
    ArrayList<Coord> salvoDupe = new ArrayList<>(currentSalvo);
    salvoDupe.removeAll(shotsThatHitOpponentShips);
    for (Coord c : salvoDupe) {
      opponentBoard[c.getY()][c.getX()].setState(State.MISS);
    }
    if(shotsThatHitOpponentShips.size() > 0) {
      this.currentMode = Mode.TARGET;
    } else {
      this.currentMode = Mode.HUNT;
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    /*
    Any useful implementation not necessary in our situation (would violate MVC)
     */
  }

  //TEMPORARY TEMPORARY
  public Coord[][] getOpponentBoard() {
    return opponentBoard;
  }
}