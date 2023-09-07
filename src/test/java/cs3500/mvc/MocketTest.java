package cs3500.mvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MocketTest {

  List<String> list1;
  List<String> list2;
  ByteArrayOutputStream output;
  ByteArrayInputStream inputs;
  Mocket mocket;

  @BeforeEach
  void setup() {
    output = new ByteArrayOutputStream();
    list1 = new ArrayList<>(List.of("a", "b", "c"));
    list2 = new ArrayList<>(List.of("a", "b", "c"));
    mocket = new Mocket(output, list1);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String s : list2) {
      printWriter.println(s);
    }
    inputs = new ByteArrayInputStream(stringWriter.toString().getBytes());

  }

  @Test
  void getInputStream() {
    try {
      assertEquals(inputs.read(), mocket.getInputStream().read());
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  void getOutputStream() {
    ByteArrayOutputStream expected = new ByteArrayOutputStream();
    assertEquals(expected.toString(), mocket.getOutputStream().toString());
  }
}