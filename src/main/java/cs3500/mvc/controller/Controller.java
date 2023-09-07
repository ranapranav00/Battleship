package cs3500.mvc.controller;

/**
 * Represents an interface for any controller Object
 */
public interface Controller {

  /**
   * Runs the game
   * Prompts user for input, initializes game state, executes gameLoop, and ends game
   */
  public void run();
}
