import java.util.List;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lexer.*;
import parser.*;
import parser.ast.ASTNode;

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
      Parser parser = new Parser(tokens);
      ASTNode ast = parser.parse();
      ast.print(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
