package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
  private final String input;
  private int pos = 0;
  private int line = 1;

  public Lexer(String input) {
    this.input = input;
  }

  public List<Token> tokenize() {
    List<Token> tokens = new ArrayList<>();

    while (!isEOF()) {
      char cur = advance();

      switch (cur) {
        case '\n':
          this.line++;
          break;
        case ' ':
        case '\t':
          break;

        case '(':
          tokens.add(token(TokenType.LPAREN, "("));
          break;
        case ')':
          tokens.add(token(TokenType.RPAREN, ")"));
          break;
        case '{':
          tokens.add(token(TokenType.LBRACE, "{"));
          break;
        case '}':
          tokens.add(token(TokenType.RBRACE, "}"));
          break;
        case ';':
          tokens.add(token(TokenType.SEMICOLON, ";"));
          break;
        case ',':
          tokens.add(token(TokenType.COMMA, ","));
          break;
        case '+':
          tokens.add(token(TokenType.PLUS, "+"));
          break;
        case '-':
          tokens.add(token(TokenType.MINUS, "-"));
          break;
        case '*':
          tokens.add(token(TokenType.MULT, "*"));
          break;
        case '/':
          tokens.add(token(TokenType.DIV, "/"));
          break;
        case '%':
          tokens.add(token(TokenType.MOD, "%"));
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

    tokens.add(token(TokenType.EOF, "\0"));
    return tokens;
  }

  private Token number(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    while (!isEOF() && Character.isDigit(input.charAt(pos))) {
      sb.append(advance());
    }
    return token(TokenType.NUMBER, sb.toString());
  }

  private Token identifier(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    while (!isEOF() && (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos) == '_')) {
      sb.append(advance());
    }

    String lexeme = sb.toString();
    switch (lexeme) {
      case "int":
        return token(TokenType.INT, lexeme);
      case "str":
        return token(TokenType.STR, lexeme);
      case "func":
        return token(TokenType.FUNC, lexeme);
      case "if":
        return token(TokenType.IF, lexeme);
      case "else":
        return token(TokenType.ELSE, lexeme);
      case "return":
        return token(TokenType.RETURN, lexeme);
      default:
        return token(TokenType.IDENTIFIER, lexeme);
    }
  }

  private Token string(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    boolean foundClosing = false;
    while (!isEOF() && input.charAt(pos) != '"') {
      sb.append(advance());
      if (!isEOF() && input.charAt(pos) == '"') {
        foundClosing = true;
        advance();
        break;
      }
    }
    if (!foundClosing) {
      throw new RuntimeException("[Error]: No closing \" found");
    }
    return token(TokenType.STRING, sb.toString());
  }

  private Token comparison(char c) {
    StringBuilder sb = new StringBuilder();
    sb.append(c);
    char next = advance();
    if (!isEOF() && next == '=') {
      sb.append(next);
    }
    String lexeme = sb.toString();
    switch (lexeme) {
      case "<":
        return token(TokenType.LT, lexeme);
      case ">":
        return token(TokenType.GT, lexeme);
      case "=":
        return token(TokenType.ASSIGN, lexeme);
      case "==":
        return token(TokenType.EQ, lexeme);
      case "<=":
        return token(TokenType.LE, lexeme);
      case ">=":
        return token(TokenType.GE, lexeme);
      case "!=":
        return token(TokenType.NE, lexeme);
      default:
        throw new RuntimeException("Should not be reached, lexeme: " + lexeme);
    }
  }

  private Token token(TokenType type, String lexeme) {
    return new Token(type, lexeme, this.line);
  }

  private char advance() {
    return input.charAt(pos++);
  }

  private boolean isEOF() {
    return pos >= input.length();
  }
}
