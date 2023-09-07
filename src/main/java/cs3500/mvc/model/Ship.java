package cs3500.mvc.model;

import cs3500.json.Direction;
import java.util.ArrayList;

/**
 * Represents a ship object
 */
public class Ship {
  private final ArrayList<Coord> coords;
  private final ShipType type;

  /**
   * A constructor for the ship object
   *
   * @param type   The type of ship it is
   * @param coords The list of coordinates the ship occupies
   */
  public Ship(ShipType type, ArrayList<Coord> coords) {
    this.type = type;
    this.coords = coords;
  }

  /**
   * Gets the type of the ship
   *
   * @return The type of the ship
   */
  public ShipType getType() {
    return this.type;
  }

  /**
   * Gets the coordinates the ship occupies
   *
   * @return the coordinates the ship occupies
   */
  public ArrayList<Coord> getCoords() {
    return coords;
  }

  /**
   * Overrode Equals, Determines equality of two ships
   *
   * @param o An object to compare with
   * @return Whether this ship is equal to the object given
   */
  public boolean equals(Object o) {
    if (o instanceof Ship) {
      if (((Ship) o).getType().equals(this.getType())) {
        for (int i = 0; i < this.getCoords().size(); i++) {
          if (((Ship) o).getCoords().get(i).getX() != this.getCoords().get(i).getX()) {
            return false;
          }
          if (((Ship) o).getCoords().get(i).getY() != this.getCoords().get(i).getY()) {
            return false;
          }
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  /**
   * Gets the direction of the ship
   *
   * @return The direction
   */
  public Direction getDirection() {
    Coord originCoord = coords.get(0);
    int originX = originCoord.getX();

    Coord secondCoord = coords.get(1);
    int secondX = secondCoord.getX();

    if (originX == secondX) {
      return Direction.VERTICAL;
    } else {
      return Direction.HORIZONTAL;
    }
  }
}