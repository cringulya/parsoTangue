package lexer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class LexerTests {
  @Test
  void emptyString() {
    Lexer lexer = new Lexer("");

    List<Token> tokens = lexer.tokenize();
    assertEquals(tokens.size(), 1);
    assertEquals(tokens.get(0).type, TokenType.EOF);
  }

  @Test
  void IntDeclaration() {
    Lexer lexer = new Lexer("int a = 0;");

    List<Token> tokens = lexer.tokenize();
    assertEquals(tokens.size(), 6);
    assertEquals(tokens.get(0).type, TokenType.INT);
    assertEquals(tokens.get(1).type, TokenType.IDENTIFIER);
    assertEquals(tokens.get(2).type, TokenType.ASSIGN);
    assertEquals(tokens.get(3).type, TokenType.NUMBER);
    assertEquals(tokens.get(4).type, TokenType.SEMICOLON);
  }
}
