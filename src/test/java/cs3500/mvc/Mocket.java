package cs3500.mvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * represents a mock socket for testing
 */
public class Mocket extends Socket {
  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * @param testLog what the server has received from the client
   * @param toSend what the server will send to the client
   */
  public Mocket(ByteArrayOutputStream testLog, List<String> toSend) {
    this.testLog = testLog;

    // Set up the list of inputs as separate messages of JSON for the ProxyDealer to handle
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String s : toSend) {
      printWriter.println(s);
    }
    this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  /**
   * gets this mocket's input stream
   *
   * @return the input stream
   */
  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  /**
   * gets this mocket's output stream
   *
   * @return the output stream
   */
  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }
}
