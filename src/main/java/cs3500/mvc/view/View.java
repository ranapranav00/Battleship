package cs3500.mvc.view;

import cs3500.mvc.model.Coord;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a View Object
 */
public class View {
  private final Scanner scan;
  private final Appendable output;

  /**
   * A constructor for the view class
   *
   * @param output The appendable object for outputs in console
   */
  public View(Readable input, Appendable output) {
    this.output = Objects.requireNonNull(output);
    scan = new Scanner(input);
  }

  /**
   * Writes the given string to the console
   *
   * @param str A string to print to the console
   */
  public void write(String str) {
    try {
      output.append(str);
    } catch (IOException e) {
      throw new RuntimeException("Something unholy has occurred.");
    }
  }

  /**
   * Displays both boards
   *
   * @param p1BoardAsString The board of player 1 visualized as a string
   * @param p2BoardAsString The board of player 2 visualized as a string
   */
  public void displayBoards(String p1BoardAsString, String p2BoardAsString) {
    try {
      output.append("Opponent Board Data:\n").append(p2BoardAsString).append("\n")
          .append("Your Board:\n").append(p1BoardAsString);
    } catch (IOException e) {
      throw new RuntimeException("Something unholy has occurred.");
    }
  }

  /**
   * Gets the next input from the console
   *
   * @return The next input from the console
   */
  public String getNext() {
    return scan.next();
  }

  /**
   * Burns any unwanted trailing inputs
   */
  public void burn() {
    scan.nextLine();
  }

  /**
   * Gets two inputs and gives it to the controller as a coordinate
   *
   * @param maxX The maximum x value a coordinate can take
   * @param maxY The maximum y value a coordinate can take
   * @return A coordinate with x and y values corresponding to what the user inputted
   */
  public Coord getNextCoord(int maxX, int maxY) {
    String strX = getNext();
    String strY = getNext();
    int x;
    int y;
    try {
      x = Integer.parseInt(strX);
      y = Integer.parseInt(strY);
      if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
        burn();
        write("Invalid coordinate Entered, Please try again.\n");
        return getNextCoord(maxX, maxY);
      }
    } catch (Exception e) {
      burn();
      write("Invalid coordinate Entered, Please try again.\n");
      return getNextCoord(maxX, maxY);
    }
    return new Coord(x, y);
  }
}
