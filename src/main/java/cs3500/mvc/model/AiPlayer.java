package cs3500.mvc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents an AI Player
 */
public class AiPlayer extends AbstractPlayer {
  private ArrayList<Coord> shotsTaken = new ArrayList<>();
  private List<Coord> unshotFromLastSalvo = new ArrayList<>();
  private int turns = 0;

  /**
   * A constructor for an AI player
   *
   * @param name The name of the player
   * @param rand A random object
   */
  public AiPlayer(String name, Random rand) {
    super(name, rand);
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    turns++;
    System.out.println(turns);
    resetHeatMap();
    int numWaterCoords = getNumWaterCoords();

    if (currentMode.equals(Mode.HUNT)) {
      ArrayList<Coord> salvo = huntMode();
      salvo.removeIf(c -> shotsTaken.contains(c));
      if (ships.size() <= numWaterCoords) {
        salvo = new ArrayList<>(salvo.subList(0, ships.size()));
      } else {
        salvo = new ArrayList<>(salvo.subList(0, numWaterCoords));
      }
      shotsTaken.addAll(salvo);
      currentSalvo = salvo;
      return salvo;
    } else {
      List<Coord> salvo = targetMode();
      salvo.removeIf(c -> shotsTaken.contains(c));
      if (salvo.size() < ships.size()) {
        ArrayList<Coord> huntedShots = huntMode();
        int i = 0;
        while (salvo.size() != ships.size()) {
          if (!salvo.contains(huntedShots.get(i)) && !shotsTaken.contains(huntedShots.get(i))) {
            salvo.add(huntedShots.get(i));
          }
          i++;
          if (ships.size() > (board.length * board[0].length) - shotsTaken.size() &&
              salvo.size() + shotsTaken.size() == board.length * board[0].length) {
            break;
          }
        }
      }
      shotsTaken.addAll(salvo);
      currentSalvo = salvo;
      return salvo;
    }
  }

  private ArrayList<Coord> huntMode() {
    Algorithm algorithm = new Algorithm(opponentBoard);
    algorithm.getPossiblePlacements(ShipType.CARRIER.getSize());
    algorithm.getPossiblePlacements(ShipType.BATTLESHIP.getSize());
    algorithm.getPossiblePlacements(ShipType.DESTROYER.getSize());
    algorithm.getPossiblePlacements(ShipType.SUB.getSize());
    algorithm.parity();
    ArrayList<Coord> allCoords = new ArrayList<>();
    for (Coord[] row : opponentBoard) {
      allCoords.addAll(Arrays.asList(row));
    }
    allCoords.sort((Coord c1, Coord c2) -> c2.getHeat() - c1.getHeat());
    return allCoords;
  }

  private List<Coord> targetMode() {
    ArrayList<Coord> nextSalvo = new ArrayList<>();
    for (Coord c : currentSalvo) {
      if (c.getState().equals(State.HIT)) {
        nextSalvo.addAll(getNextSalvo(c));
      }
    }
    if (nextSalvo.size() > ships.size()) {
      unshotFromLastSalvo.addAll(nextSalvo.subList(ships.size() - 1, nextSalvo.size()));
      return nextSalvo.subList(0, ships.size());
    }
    return nextSalvo;
  }

  private int getNumWaterCoords() {
    int count = 0;
    for (Coord[] row : opponentBoard) {
      for (Coord c : row) {
        if (c.getState().equals(State.WATER)) {
          count++;
        }
      }
    }
    return count;
  }

  private ArrayList<Coord> getNextSalvo(Coord c) {
    ArrayList<Coord> nextSalvo = getSurroundings(c);
    nextSalvo.removeIf(coord -> !coord.getState().equals(State.WATER));
    return nextSalvo;
  }

  private ArrayList<Coord> getSurroundings(Coord c) {
    ArrayList<Coord> nextSalvo = new ArrayList<>();
    if (c.getY() + 1 < opponentBoard.length) {
      nextSalvo.add(opponentBoard[c.getY() + 1][c.getX()]);
    }
    if (c.getY() - 1 >= 0) {
      nextSalvo.add(opponentBoard[c.getY() - 1][c.getX()]);
    }
    if (c.getX() + 1 < opponentBoard[0].length) {
      nextSalvo.add(opponentBoard[c.getY()][c.getX() + 1]);
    }
    if (c.getX() - 1 >= 0) {
      nextSalvo.add(opponentBoard[c.getY()][c.getX() - 1]);
    }
    return nextSalvo;
  }

  private void resetHeatMap() {
    for (Coord[] row : opponentBoard) {
      for (Coord c : row) {
        c.setHeat(0);
      }
    }
  }
}