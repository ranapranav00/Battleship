package cs3500.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a game model object
 */
public class GameModel {
  private final Player player1;
  private Coord[][] player1Board;
  private List<Ship> player1Ships;
  private final Player player2;
  private Coord[][] player2Board;
  private List<Ship> player2Ships;
  private int height;
  private int width;
  private Map<ShipType, Integer> shipCount;

  /**
   * A constructor for the game model
   *
   * @param player1 The first player
   * @param player2 The second player
   */
  public GameModel(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * Initializes the state of the game by generating a list of ships and board for each player
   */
  public void start() {
    player1Ships = player1.setup(height, width, shipCount);
    player1Board = createBoard(player1Ships);
    player2Ships = player2.setup(height, width, shipCount);
    player2Board = createBoard(player2Ships);
  }

  /**
   * Executes the gameLoop of the game, where players shoot at the enemy fleet
   * State of boards change to reflect misses and hits on each board
   */
  public void gameLoop() {
    List<Coord> p1AllShots = player1.takeShots();
    List<Coord> p2AllShots = player2.takeShots();

    List<Coord> p2HitsOnP1 = player1.reportDamage(p2AllShots);
    List<Coord> p1HitsOnP2 = player2.reportDamage(p1AllShots);

    player1.successfulHits(p1HitsOnP2);
    player2.successfulHits(p2HitsOnP1);

    List<Coord> p2MissesOnP1 = extractMisses(p2AllShots, p2HitsOnP1);
    List<Coord> p1MissesOnP2 = extractMisses(p1AllShots, p1HitsOnP2);

    updateState(p2MissesOnP1, p2HitsOnP1, player1Board, player1Ships);
    updateState(p1MissesOnP2, p1HitsOnP2, player2Board, player2Ships);
  }

  /**
   * Creates a board by populating it with water, then using a player list of ships to populate
   * it with ships
   *
   * @param ships A list of ships to populate the board with
   * @return The board created
   */
  private Coord[][] createBoard(List<Ship> ships) {
    Coord[][] board = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Coord(j, i);
      }
    }
    for (Ship ship : ships) {
      for (Coord coord : ship.getCoords()) {
        board[coord.getY()][coord.getX()].setState(State.SHIP);
      }
    }
    return board;
  }

  /**
   * Given a list of all shots, and the list of those shots that hit, returns a list of only the
   * missed shots
   *
   * @param allShots A list of all shots taken
   * @param hits     A list of all hits from all the shots
   * @return A list of all misses from all the shots
   */
  private ArrayList<Coord> extractMisses(List<Coord> allShots, List<Coord> hits) {
    ArrayList<Coord> misses = new ArrayList<>();
    for (Coord coord : allShots) {
      boolean shot = false;
      for (Coord hit : hits) {
        if (hit.getX() == coord.getX() && hit.getY() == coord.getY()) {
          shot = true;
          break;
        }
      }
      if (!shot) {
        misses.add(coord);
      }
    }
    return misses;
  }

  /**
   * Updates the state of the game by updating the boards, and the list of ships
   *
   * @param misses A list of missed shots
   * @param hits   A list of hit shots
   * @param board  The board to be updated
   * @param ships  The list of ships to be updated
   */
  private void updateState(List<Coord> misses, List<Coord> hits, Coord[][] board,
                           List<Ship> ships) {

    for (Coord coord : misses) {
      board[coord.getY()][coord.getX()].setState(State.MISS);
    }

    for (Coord coord : hits) {
      board[coord.getY()][coord.getX()].setState(State.HIT);
    }

    ArrayList<Ship> copyOfShips = new ArrayList<>(ships);
    for (Ship ship : copyOfShips) {
      boolean sunk = true;
      for (Coord coord : ship.getCoords()) {
        if (board[coord.getY()][coord.getX()].getState().equals(State.SHIP)) {
          sunk = false;
          break;
        }
      }
      if (sunk) {
        ships.remove(ship);
      }
    }
  }

  /**
   * Sets the shipCount to the given shipCount
   *
   * @param shipCount A map of types of ships and the corresponding amount of this type
   */
  public void setShipCount(Map<ShipType, Integer> shipCount) {
    this.shipCount = shipCount;
  }

  /**
   * Gets the map of ships and their respective counts
   *
   * @return The map of ships and their respective counts
   */
  public Map<ShipType, Integer> getShipCount() {
    return shipCount;
  }

  /**
   * Sets the height of the board
   *
   * @param height The height of the board
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Gets the height of the board
   *
   * @return Height of the board
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the width of the board
   *
   * @param width the width of the board
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Gets the width of the board
   *
   * @return Width of the Board
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets a string representation of the board
   *
   * @param isPlayer2 Whether the player is player2 or not
   * @return A string representation of the board depending on the player
   */
  public String getBoardAsString(Boolean isPlayer2) {
    StringBuilder boardAsString = new StringBuilder();
    Coord[][] board = player1Board;
    if (isPlayer2) {
      board = player2Board;
    }
    for (Coord[] coords : board) {
      for (Coord coord : coords) {
        String s = coord.getStringRep() + " ";
        if (isPlayer2 && coord.getState().equals(State.SHIP)) {
          s = State.WATER.getStringRep() + " ";
        }
        boardAsString.append(s);
      }
      boardAsString.append("\n");
    }
    return boardAsString.toString();
  }

  /**
   * A list of player 1's surviving ships
   * Could have combined getP1P2Ships into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @return Player 1's surviving ships
   */
  public List<Ship> getPlayer1Ships() {
    return player1Ships;
  }

  /**
   * A list of player 2's surviving ships
   * Could have combined getP1P2Ships into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @return Player 2's surviving ships
   */
  public List<Ship> getPlayer2Ships() {
    return player2Ships;
  }

  /**
   * Gets Player 1's board
   * Could have combined getP1P2Boards into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @return Player 1's board
   */
  public Coord[][] getP1Board() {
    return player1Board;
  }

  /**
   * Gets Player 2's board
   * Could have combined getP1P2Board into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @return Player 2's board
   */
  public Coord[][] getP2Board() {
    return player2Board;
  }

  /**
   * Sets the Ships of player1 to the given list of ships
   * Could have combined setP1P2Ships into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @param ships A list of ships
   */
  public void setPlayer1Ships(List<Ship> ships) {
    this.player1Ships = ships;
  }

  /**
   * Sets the Ships of player2 to the given list of ships
   * Could have combined setP1P2Ships into one, but did not for sake of readability
   * Easier to read individually than having a boolean passed into 1 method to determine which
   * to get
   *
   * @param ships A list of ships
   */
  public void setPlayer2Ships(List<Ship> ships) {
    this.player2Ships = ships;
  }


  /**
   * Ends the game and returns a string to be shown to the user
   *
   * @return A string of why the game ended
   */
  public String endGame() {
    String endMsg = "";
    if (player1Ships.size() == 0 && player2Ships.size() == 0) {
      endMsg = "You tied!";
    } else if (player1Ships.size() == 0) {
      endMsg = "Your fleet was destroyed!";
    } else if (player2Ships.size() == 0) {
      endMsg = "You destroyed the enemy fleet!";
    }
    return endMsg;
  }

  public String makeHeatMap() {
    StringBuilder heatmap = new StringBuilder();
    for(Coord[] row : player2.getOpponentBoard()) {
      for(Coord c : row) {
        heatmap.append(c.getHeat()).append(" ");
      }
      heatmap.append("\n");
    }
    return heatmap.toString();
  }
}