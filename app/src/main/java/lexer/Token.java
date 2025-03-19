package lexer;

public class Token {
  public TokenType type;
  public String lexeme;
  public int line;

  Token(TokenType type, String lexeme, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.line = line;
  }

  public String toString() {
    return type + "(" + lexeme + ")";
  }
}
