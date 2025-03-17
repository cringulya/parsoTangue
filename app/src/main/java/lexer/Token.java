package lexer;

public class Token {
  public TokenType type;
  public String lexeme;

  Token(TokenType type, String lexeme) {
    this.type = type;
    this.lexeme = lexeme;
  }

  public String toString() {
    return type + "{" + lexeme + "}";
  }
}
