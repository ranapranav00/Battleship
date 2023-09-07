package cs3500.json;

/**
 * represents different messages
 */
public enum Message {
  JOIN("join"),
  SETUP("setup"),
  TAKESHOTS("take-shots"),
  REPORTDAMAGE("report-damage"),
  SUCCESSFULHITS("successful-hits"),
  ENDGAME("end-game");

  private final String methodName;

  Message(String methodName) {
    this.methodName = methodName;
  }

  public String getMethodName() {
    return methodName;
  }
}
