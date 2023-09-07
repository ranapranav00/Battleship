package cs3500.mvc.model;

/**
 * Represents the State of a Coordinate
 */
public enum State {
  SHIP("S"),
  WATER("~"),
  HIT("#"),
  MISS("x");

  private final String stringRep;

  /**
   * A constructor for the State enum
   *
   * @param stringRep A string representation of the different types
   */
  State(String stringRep) {
    this.stringRep = stringRep;
  }

  /**
   * Gets the string representation of a State
   *
   * @return The string representation of a State
   */
  public String getStringRep() {
    return stringRep;
  }
}
