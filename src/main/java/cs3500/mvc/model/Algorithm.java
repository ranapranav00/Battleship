package cs3500.mvc.model;

import java.util.ArrayList;

public class Algorithm {
  private Coord[][] board;
  private final int height;
  private final int width;
  public Algorithm(Coord[][] board) {
    this.board = board;
    this.height = board.length;
    this.width = board[0].length;
  }


  /**
   * Gets the possible placements of a ship of the given size
   *
   * @param size The size of the ship
   * @return The possible list of placements on the board
   */
  public ArrayList<ArrayList<Coord>> getPossiblePlacements(int size) {
    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
    listOfPlacements.addAll(getHorizontalPlacements(size, board));
    listOfPlacements.addAll(getVerticalPlacements(size, board));
    return listOfPlacements;
  }

  /**
   * Gets the possible horizontal placements on the board
   *
   * @param size The size of the ship
   * @return All possible horizontal placements for a ship of this size on the board
   */

  private ArrayList<ArrayList<Coord>> getHorizontalPlacements(int size, Coord[][] board) {
    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        ArrayList<Coord> placement = new ArrayList<>();
        for (int k = 0; k < size; k++) {
          if (j + k >= width) {
            break;
          }
          Coord coord = board[i][j + k];
          if (coord.getState().equals(State.WATER) || coord.getState().equals(State.HIT)) {
            placement.add(coord);
          }
        }
        if (placement.size() == size) {
          for(Coord c : placement) {
            c.incrementHeat();
          }
          listOfPlacements.add(placement);
        }
      }
    }
    return listOfPlacements;
  }

  /**
   * Gets all the vertical placements of a ship of the given size on the board
   *
   * @param size The size of the ship
   * @return All possible vertical placements of this ship size on the board
   */
  private ArrayList<ArrayList<Coord>> getVerticalPlacements(int size, Coord[][] board) {
    ArrayList<ArrayList<Coord>> listOfPlacements = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        ArrayList<Coord> placement = new ArrayList<>();
        for (int k = 0; k < size; k++) {
          if (j + k >= height) {
            break;
          }
          Coord coord = board[j + k][i];
          if (coord.getState().equals(State.WATER) || coord.getState().equals(State.HIT)) {
            placement.add(coord);
          }
        }
        if (placement.size() == size) {
          for(Coord c : placement) {
            c.incrementHeat();
          }
          listOfPlacements.add(placement);
        }
      }
    }
    return listOfPlacements;
  }

  public void parity() {
    for(Coord[] row : board) {
      for(Coord c : row) {
        if((c.getY() + c.getX()) % 3 == 0) {
          c.setHeat(c.getHeat() * 100);
        }
      }
    }
  }
}
