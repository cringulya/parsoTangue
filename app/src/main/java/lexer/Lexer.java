package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private final String input;
  private int pos = 0;

  public Lexer(String input) {
    this.input = input;
  }

  public List<Token> tokenize() {
    List<Token> tokens = new ArrayList<>();

    while (!isEOF()) {
      char cur = advance();

      switch (cur) {
        case ' ':
        case '\n':
        case '\t':
          break;

        case '(':
          tokens.add(new Token(TokenType.LPAREN, "("));
          break;
        case ')':
          tokens.add(new Token(TokenType.RPAREN, ")"));
          break;
        case '{':
          tokens.add(new Token(TokenType.LBRACE, "{"));
          break;
        case '}':
          tokens.add(new Token(TokenType.RBRACE, "}"));
          break;
        case ';':
          tokens.add(new Token(TokenType.SEMICOLON, ";"));
          break;
        case ',':
          tokens.add(new Token(TokenType.COMMA, ","));
          break;
        case '+':
          tokens.add(new Token(TokenType.PLUS, "+"));
          break;
        case '-':
          tokens.add(new Token(TokenType.MINUS, "-"));
          break;
        case '*':
          tokens.add(new Token(TokenType.MULT, "*"));
          break;
        case '/':
          tokens.add(new Token(TokenType.DIV, "*"));
          break;
        case '%':
          tokens.add(new Token(TokenType.MOD, "*"));
          break;
        case '"':
          tokens.add(string(advance()));
          break;
        case '>':
        case '<':
        case '=':
        case '!':
          tokens.add(comparison(cur));
          break;
        default:
          if (Character.isDigit(cur)) {
            tokens.add(number(cur));
          } else if (Character.isLetter(cur)) {
            tokens.add(identifier(cur));
          }
          break;
      }
    }

    tokens.add(new Token(TokenType.EOF, "\0"));
    return tokens;
  }

  private Token number(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    while (!isEOF() && Character.isDigit(input.charAt(pos))) {
      sb.append(advance());
    }
    return new Token(TokenType.NUMBER, sb.toString());
  }

  private Token identifier(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    while (!isEOF() && Character.isLetterOrDigit(input.charAt(pos))) {
      sb.append(advance());
    }

    String lexeme = sb.toString();
    switch (lexeme) {
      case "var":
        return new Token(TokenType.VAR, lexeme);
      case "func":
        return new Token(TokenType.FUNC, lexeme);
      case "if":
        return new Token(TokenType.IF, lexeme);
      case "else":
        return new Token(TokenType.ELSE, lexeme);
      case "return":
        return new Token(TokenType.RETURN, lexeme);
      default:
        return new Token(TokenType.IDENTIFIER, lexeme);
    }
  }

  private Token string(char c) {
    StringBuilder sb = new StringBuilder();
    boolean foundClosing = false;
    while (!isEOF() && input.charAt(pos) != '"') {
      sb.append(advance());
      if (input.charAt(pos) == '"') {
        foundClosing = true;
      }
    }
    if (!foundClosing) {
      throw new RuntimeException("[Error]: No closing \" found");
    }
    return new Token(TokenType.STRING, sb.toString());
  }

  private Token comparison(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    char next = advance();
    if (!isEOF() && next == '=') {
      sb.append(c);
    }
    String lexeme = sb.toString();
    switch (lexeme) {
      case "<":
        return new Token(TokenType.LT, lexeme);
      case ">":
        return new Token(TokenType.GT, lexeme);
      case "=":
        return new Token(TokenType.ASSIGN, lexeme);
      case "==":
        return new Token(TokenType.EQ, lexeme);
      case "<=":
        return new Token(TokenType.LE, lexeme);
      case ">=":
        return new Token(TokenType.GE, lexeme);
      case "!=":
        return new Token(TokenType.NE, lexeme);
      default:
        throw new RuntimeException("Should not be reached");
    }
  }

  private char advance() {
    return input.charAt(pos++);
  }

  private boolean isEOF() {
    return pos >= input.length();
  }
}
