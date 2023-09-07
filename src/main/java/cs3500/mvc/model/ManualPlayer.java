package cs3500.mvc.model;

import cs3500.mvc.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a Manual Player
 */
public class ManualPlayer extends AbstractPlayer {

  /**
   * A constructor for a manual player
   *
   * @param name The name of the player
   * @param rand A random object
   */
  public ManualPlayer(String name, Random rand) {
    super(name, rand);
  }

  /**
   * Another constructor for the manual player used during testing with set random seeds
   *
   * @param name The name of the player
   * @param rand The random seed
   * @param input A Readable object
   * @param output An Appendable object
   */
  public ManualPlayer(String name, Random rand, Readable input, Appendable output) {
    super(name, rand);
    this.view = new View(input, output);
  }
  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */

  @Override
  public List<Coord> takeShots() {
    ArrayList<Coord> shots = new ArrayList<>();
    for (int i = 0; i < ships.size(); i++) {
      Coord shot = view.getNextCoord(board[0].length, board.length);
      shots.add(shot);
    }
    return shots;
  }
}