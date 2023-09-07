package cs3500.mvc.model;

/**
 * Represents a Coordinate Object
 */
public class Coord {
  private final int xval;
  private final int yval;
  private State state;
  private int heat;

  /**
   * A constructor for a coordinate
   * Answer to Question@902 on Piazza said design is up to us, so my coordinate
   * acts as a position in a 2D Array, so new Coord(3,0) is the same position as Coord[3][0]
   * (First coordinate is horizontal position, Second coordinate is vertical position)
   *
   * @param xval the position on the height on the board
   * @param yval the position on the width on the board
   */
  public Coord(int xval, int yval) {
    this.xval = xval;
    this.yval = yval;
    this.state = State.WATER;
    this.heat = 0;
  }

  /**
   * Gets the x value
   *
   * @return x value
   */
  public int getX() {
    return xval;
  }

  /**
   * Gets the y value
   *
   * @return y value
   */
  public int getY() {
    return yval;
  }

  /**
   * Sets the state of the coordinate to the given state
   *
   * @param state the state to change the coordinate to
   */
  public void setState(State state) {
    this.state = state;
  }

  /**
   * Gets the state of the coordinate
   *
   * @return the state of the coordinate
   */
  public State getState() {
    return state;
  }

  /**
   * Gets a string representation of the coordinate
   *
   * @return A string representation of the coordinate
   */
  public String getStringRep() {
    return state.getStringRep();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Coord) {
      return ((Coord) o).getX() == getX() && ((Coord) o).getY() == getY()
          && ((Coord) o).getState() == getState();
    }
    return false;
  }

  public int getHeat() {
    return heat;
  }

  public void setHeat(int i) {
    this.heat = i;
  }

  public void incrementHeat() {
    heat++;
  }
}