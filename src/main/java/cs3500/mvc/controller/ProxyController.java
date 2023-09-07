package cs3500.mvc.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.json.CoordJson;
import cs3500.json.CoordinatesJson;
import cs3500.json.EndGameJson;
import cs3500.json.FleetJson;
import cs3500.json.FleetSpecsJson;
import cs3500.json.GameType;
import cs3500.json.JoinJson;
import cs3500.json.JsonUtils;
import cs3500.json.Message;
import cs3500.json.MessageJson;
import cs3500.json.SetupJson;
import cs3500.json.ShipJson;
import cs3500.mvc.model.Coord;
import cs3500.mvc.model.Player;
import cs3500.mvc.model.Ship;
import cs3500.mvc.model.ShipType;
import cs3500.mvc.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a ProxyController object used for Local vs Server games
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream input;
  private final PrintStream output;
  private final Player player;
  private final View view;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Represents a constructor for a proxyController
   *
   * @param server The server we are playing against
   * @param player An aiPlayer to play against the server
   */
  public ProxyController(Socket server, Player player) {
    this.server = server;
    this.player = player;
    this.view = new View(new InputStreamReader(System.in), System.out);
    try {
      this.input = server.getInputStream();
      this.output = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Runs the game
   * Parses the json into a messageJson and calls delegateMessage to handle the rest, and prints
   * the client side response
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.input);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        JsonNode response = delegateMessage(message);
        this.output.println(response);
      }
    } catch (IOException e) {
      view.write("\n There was an error receiving messages.\n");
    }
  }

  /**
   * Given a message by the run method, creates another messageJson to respond with, and
   * sets it to the return of the appropriate handler method, serializes it to a jsonNode
   * and returns that
   *
   * @param message The messageJson received from the server
   *
   * @return Returns a jsonNode of the local response to the server
   */
  private JsonNode delegateMessage(MessageJson message) {
    String messageName = message.name();
    JsonNode arguments = message.arguments();
    MessageJson responseJson = new MessageJson(messageName, mapper.createObjectNode());
    JsonNode serverResponse;

    if (Message.JOIN.getMethodName().equals(messageName)) {
      responseJson = handleJoin();
    } else if (Message.SETUP.getMethodName().equals(messageName)) {
      responseJson = handleSetup(arguments);
    } else if (Message.TAKESHOTS.getMethodName().equals(messageName)) {
      responseJson = handleTakeShots();
    } else if (Message.REPORTDAMAGE.getMethodName().equals(messageName)) {
      responseJson = handleReportDamage(arguments);
    } else if (Message.SUCCESSFULHITS.getMethodName().equals(messageName)) {
      handleSuccessfulHits(arguments);
    } else if (Message.ENDGAME.getMethodName().equals(messageName)) {
      handleEndGame(arguments);
    }

    serverResponse = JsonUtils.serializeRecordToJson(responseJson);
    return serverResponse;
  }

  /**
   * Handles the case that the server message is a join message
   *
   * @return The client response to a server join request
   */
  private MessageJson handleJoin() {
    JoinJson joinJson = new JoinJson(player.name(), GameType.valueOf("MULTI"));

    JsonNode messageArguments = JsonUtils.serializeRecordToJson(joinJson);

    return new MessageJson("join", messageArguments);
  }

  /**
   * Handles the case that the server message is a setup message
   *
   * @param setupMsg The setup message
   *
   * @return The client response to a server setup request
   */
  private MessageJson handleSetup(JsonNode setupMsg) {
    SetupJson setupJson = mapper.convertValue(setupMsg, SetupJson.class);
    FleetSpecsJson fleetSpecsJson = mapper.convertValue(setupJson.fleetSpecs(),
        FleetSpecsJson.class);

    Map<ShipType, Integer> fleetSpecs = new HashMap<>();
    fleetSpecs.put(ShipType.CARRIER, fleetSpecsJson.numCarrier());
    fleetSpecs.put(ShipType.BATTLESHIP, fleetSpecsJson.numBattleships());
    fleetSpecs.put(ShipType.DESTROYER, fleetSpecsJson.numDestroyers());
    fleetSpecs.put(ShipType.SUB, fleetSpecsJson.numSubmarines());

    List<Ship> ships = player.setup(setupJson.height(), setupJson.width(), fleetSpecs);
    FleetJson fleetJson = adaptToFleetJson(ships);
    JsonNode messageArguments = mapper.convertValue(fleetJson, JsonNode.class);
    return new MessageJson("setup", messageArguments);
  }

  /**
   * Handles the case that the server message is a take-shots message
   *
   * @return The client response to a server sending its take-shots
   */
  private MessageJson handleTakeShots() {
    List<Coord> shots = player.takeShots();
    CoordinatesJson coordinatesJson = adaptToCoordinatesJson(shots);
    JsonNode messageArguments = mapper.convertValue(coordinatesJson, JsonNode.class);
    return new MessageJson("take-shots", messageArguments);
  }

  /**
   * Handles the case that the server message is a report-damage message
   *
   * @param reportDmgMsg The report-damage message
   *
   * @return The client response to a server sending a report-damage message
   */
  private MessageJson handleReportDamage(JsonNode reportDmgMsg) {
    CoordinatesJson coordinatesJson = mapper.convertValue(reportDmgMsg, CoordinatesJson.class);
    List<Coord> damage = player.reportDamage(adaptToCoordinates(coordinatesJson));
    JsonNode messageArguments = mapper.convertValue(adaptToCoordinatesJson(damage), JsonNode.class);

    return new MessageJson("report-damage", messageArguments);
  }

  /**
   * Handles the case that the server message is a successful-hits message
   *
   * @param successfulHitsMsg The successful-hits message
   */
  private void handleSuccessfulHits(JsonNode successfulHitsMsg) {
    CoordinatesJson coordinatesJson = mapper.convertValue(successfulHitsMsg, CoordinatesJson.class);
    List<Coord> hits = adaptToCoordinates(coordinatesJson);
    player.successfulHits(hits);
  }

  /**
   * Handles the case that the server message is a end-game message
   *
   * @param endGameMsg The end-game message
   */
  private void handleEndGame(JsonNode endGameMsg) {
    EndGameJson endGameJson = mapper.convertValue(endGameMsg, EndGameJson.class);
    view.write("You " + endGameJson.result() + " " + endGameJson.reason());
    try {
      server.close();
    } catch (IOException e) {
      view.write("Could not close server.");
    }
  }

  /**
   * Adapts a coordinatesJson to a list of coordinates
   *
   * @param coordinatesJson A coordinatesJson to be converted to a list of coordinates
   *
   * @return The converted list of coordinates
   */
  private List<Coord> adaptToCoordinates(CoordinatesJson coordinatesJson) {
    List<Coord> coords = new ArrayList<>();

    for (int i = 0; i < coordinatesJson.shots().size(); i++) {
      Coord coord = adaptToCoord(coordinatesJson.shots().get(i));
      coords.add(coord);
    }
    return coords;
  }

  /**
   * Adapts a CoordJson into a Coord object
   *
   * @param coordJson A CoordJson to convert into a coord
   *
   * @return The converted coord
   */
  private Coord adaptToCoord(CoordJson coordJson) {
    return new Coord(coordJson.x(), coordJson.y());
  }

  /**
   * Converts a list of ships into a FleetJson object
   *
   * @param ships the list of ships to convert
   *
   * @return The converted FleetJson object
   */
  private FleetJson adaptToFleetJson(List<Ship> ships) {
    List<ShipJson> shipJsons = new ArrayList<>();

    for (Ship ship : ships) {
      shipJsons.add(adaptToShipJson(ship));
    }

    return new FleetJson(shipJsons);
  }

  /**
   * Adapts a ship to a ShipJson
   *
   * @param ship A ship to convert into a ShipJson
   *
   * @return The converted ShipJson object
   */
  private ShipJson adaptToShipJson(Ship ship) {
    Coord least = ship.getCoords().get(0);
    return new ShipJson(adaptToCoordJson(least), ship.getType().getSize(), ship.getDirection());
  }

  /**
   * Adapts a list of coords into a CoordinatesJson
   *
   * @param coords A list of coords to convert into a CoordinatesJson
   *
   * @return The converted CoordinatesJson
   */
  private CoordinatesJson adaptToCoordinatesJson(List<Coord> coords) {
    List<CoordJson> coordJsons = new ArrayList<>();
    for (Coord coord : coords) {
      coordJsons.add(adaptToCoordJson(coord));
    }

    return new CoordinatesJson(coordJsons);
  }

  /**
   * Adapts a coord object into a CoordJson
   *
   * @param coord A coord to be converted
   *
   * @return The converted CoordJson
   */
  private CoordJson adaptToCoordJson(Coord coord) {
    return new CoordJson(coord.getX(), coord.getY());
  }
}