package cs3500.mvc.view;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.mvc.model.Coord;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the View class
 */
class ViewTest {
  private View view;
  private Appendable output;
  private String boardDisplay;
  private String p1StrBoard;
  private String p2StrBoard;
  private String smallInputMsg;
  private String bigInputMsg;

  @BeforeEach
  void setup() {
    output = new StringBuilder();
    Readable reader = new StringReader("");
    view = new View(reader, output);
    smallInputMsg = "Invalid coordinate Entered, Please try again.\n";
    bigInputMsg = """
        Invalid coordinate Entered, Please try again.
        Invalid coordinate Entered, Please try again.
        Invalid coordinate Entered, Please try again.
        Invalid coordinate Entered, Please try again.
        """;
    p1StrBoard = """
        ~ ~ S S S ~\s
        S S S S S S\s
        ~ ~ ~ ~ ~ ~\s
        S S S S ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        S S S S S ~""";
    p2StrBoard = """
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        """;
    boardDisplay = """
        Opponent Board Data:
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        ~ ~ ~ ~ ~ ~\s

        Your Board:
        ~ ~ S S S ~\s
        S S S S S S\s
        ~ ~ ~ ~ ~ ~\s
        S S S S ~ ~\s
        ~ ~ ~ ~ ~ ~\s
        S S S S S ~""";
  }

  /**
   * Tests the Write Method
   */
  @Test
  void testWrite() {
    view.write("S S S S S");
    assertEquals("S S S S S", output.toString());
  }

  /**
   * Tests the DisplayBoards method
   */
  @Test
  void testDisplayBoards() {
    view.displayBoards(p1StrBoard, p2StrBoard);
    assertEquals(boardDisplay, output.toString());
  }

  /**
   * Tests the getNext method
   */
  @Test
  void testGetNext() {
    Readable read4 = new StringReader("4");
    View view = new View(read4, output);
    assertEquals("4", view.getNext());
  }

  /**
   * Tests the burn method
   */
  @Test
  void testBurn() {
    Readable read4 = new StringReader("4 4 this will get ignored\n ThisMessage");
    View view = new View(read4, output);
    view.getNext();
    view.getNext();
    view.burn();
    assertEquals("ThisMessage", view.getNext());
  }

  /**
   * Tests the getNextCoord method in the case that a normal coordinate is entered
   */
  @Test
  void testGetNextCoordNormal() {
    Readable coord00 = new StringReader("0 0");
    View view = new View(coord00, output);
    Coord coord = view.getNextCoord(6, 6);
    assertEquals(0, coord.getX());
    assertEquals(0, coord.getY());
  }

  /**
   * Tests the getNextCoord method in the case that a string is entered
   */
  @Test
  void testGetNextCoordString() {
    setup();
    Readable coordaaErr42 = new StringReader("a a \n 4 2");
    View view3 = new View(coordaaErr42, output);
    Coord coord42 = view3.getNextCoord(6, 6);
    assertEquals(smallInputMsg, output.toString());
    assertEquals(4, coord42.getX());
    assertEquals(2, coord42.getY());
  }

  /**
   * Tests the getNextCoord method in the case that the coord entered is out of bounds
   */
  @Test
  void testGetNextInvalidCoord() {
    setup();
    Readable allCasesCoords = new StringReader("9 1 \n -1 1 \n 1 -1 \n 1 9 \n 3 3");
    View view5 = new View(allCasesCoords, output);
    Coord coord33 = view5.getNextCoord(6, 6);
    assertEquals(bigInputMsg, output.toString());
    assertEquals(3, coord33.getX());
    assertEquals(3, coord33.getY());
  }
}