package cs3500.driver;

import cs3500.mvc.controller.Controller;
import cs3500.mvc.controller.LocalController;
import cs3500.mvc.controller.ProxyController;
import cs3500.mvc.model.AiPlayer;
import cs3500.mvc.model.ManualPlayer;
import cs3500.mvc.model.Player;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      Controller controller;

      String host = args[0];
      int port;
      Socket socket;

      try {
        port = Integer.parseInt(args[1]);
        socket = new Socket(host, port);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid port provided");
      } catch (IOException e) {
        throw new IllegalStateException("Socket could not be created");
      }

      Player player = new AiPlayer("pa04-360", new Random());

      controller = new ProxyController(socket, player);
      controller.run();

    } else if (args.length == 0) {
      Player p1 = new ManualPlayer("User", new Random());
      Player p2 = new AiPlayer("BattleShipAI", new Random());
      LocalController localController = new LocalController(p1, p2);
        localController.run();
    } else if (args.length == 3) {
      // 10.110.107.123 - Local port
      // 0.0.0.0 35001 - Tournament
      System.out.println("HI HI HI");
    } else {
      throw new IllegalStateException("You must have either 0 or 2 arguments.");
    }
  }
}
