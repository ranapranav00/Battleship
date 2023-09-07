package cs3500.mvc.model;

/**
 * Represents a Ship Type
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUB(3);

  private final int size;

  /**
   * Represents a constructor for a Ship Type
   *
   * @param size The size of a ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the Size of a ship depending on the enum
   *
   * @return the size of the ship
   */
  public int getSize() {
    return size;
  }
}
