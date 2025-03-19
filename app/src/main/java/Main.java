import java.util.List;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lexer.*;
import parser.*;
import parser.ast.ASTNode;

public class Main {

  public static void main(String[] args) {
    String file = "test.pt";
    if (args.length > 0) {
      file = args[0];
    }

    try {
      InputStream is = Main.class.getClassLoader().getResourceAsStream(file);
      if (is == null) {
        System.err.println("[Error] File not found in resources! (src/main/resources)");
        return;
      }
      String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);
      Lexer lexer = new Lexer(input);
      List<Token> tokens = lexer.tokenize();
      Parser parser = new Parser(tokens);
      ASTNode ast = parser.parse();
      ast.print(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
