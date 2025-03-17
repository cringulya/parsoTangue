import java.util.List;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lexer.*;

public class Main {

  public static void main(String[] args) {
    try {
      InputStream is = Main.class.getClassLoader().getResourceAsStream("test.pt");
      if (is == null) {
        System.err.println("File not found in resources!");
        return;
      }
      String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);
      Lexer lexer = new Lexer(input);
      List<Token> tokens = lexer.tokenize();
      for (Token tok : tokens) {
        System.out.println(tok.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
