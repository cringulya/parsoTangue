package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import lexer.TokenType;
import parser.ast.ASTNode;
import parser.ast.BinaryExpression;
import parser.ast.EntryNode;
import parser.ast.LiteralNode;
import parser.ast.VarDeclarationNode;

public class Parser {
  private final List<Token> tokens;
  private int pos = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public EntryNode parse() {
    List<ASTNode> declarations = new ArrayList<>();
    while (!end()) {
      declarations.add(parseDeclaration());
    }
    return new EntryNode(declarations);
  }

  private ASTNode parseDeclaration() {
    if (match(TokenType.VAR)) {
      return parseVarDeclaration();
    }
    throw new RuntimeException("not implemented");
  }

  private ASTNode parseVarDeclaration() {
    Token identifier = consume(TokenType.IDENTIFIER, "expecting variable name");
    consume(TokenType.ASSIGN, "expecting =");
    ASTNode expression = parseExpression();
    consume(TokenType.SEMICOLON, "expecting ;");
    return new VarDeclarationNode(identifier.toString(), expression);
  }

  private ASTNode parseExpression() {
    ASTNode left = parseTerm();
    while (check(TokenType.PLUS) || check(TokenType.MINUS)) {
      Token op = advance();
      ASTNode right = parseTerm();
      left = new BinaryExpression(left, op.lexeme, right);
    }
    return left;
  }

  private ASTNode parseTerm() {
    ASTNode left = parseFactor();
    if (check(TokenType.MULT) || check(TokenType.DIV)) {
      Token op = advance();
      ASTNode right = parseFactor();
      return new BinaryExpression(left, op.lexeme, right);
    }
    return left;
  }

  private ASTNode parseFactor() {
    if (check(TokenType.NUMBER)) {
      Token tok = advance();
      return new LiteralNode(tok.toString());
    } else if (match(TokenType.LPAREN)) {
      ASTNode expr = parseExpression();
      consume(TokenType.RPAREN, "expecting )");
      return expr;
    } else {
      throw new RuntimeException("[Error] unexpected token");
    }
  }

  private boolean match(TokenType type) {
    if (check(type)) {
      advance();
      return true;
    }
    return false;
  }

  private Token consume(TokenType type, String message) {
    if (check(type))
      return advance();
    throw error(top(), "[Error] " + message);
  }

  private boolean check(TokenType type) {
    if (end())
      return false;
    return top().type == type;
  }

  private boolean end() {
    return top().type == TokenType.EOF;
  }

  private Token top() {
    return tokens.get(pos);
  }

  private Token advance() {
    if (!end())
      pos++;
    return tokens.get(pos - 1);
  }

  private RuntimeException error(Token token, String message) {
    return new RuntimeException("Error at token " + token.lexeme + ": " + message);
  }

}
