package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import lexer.TokenType;
import parser.ast.ASTNode;
import parser.ast.BinaryExpression;
import parser.ast.BlockNode;
import parser.ast.ComparisonExpression;
import parser.ast.FunctionDeclarationNode;
import parser.ast.IfNode;
import parser.ast.LiteralNode;
import parser.ast.ReturnStatement;
import parser.ast.VarDeclarationNode;

public class Parser {
  private final List<Token> tokens;
  private int pos = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public BlockNode parse() {
    return parseBlock();
  }

  private BlockNode parseBlock() {
    List<ASTNode> instructions = new ArrayList<>();
    while (!end() && !check(TokenType.RBRACE)) {
      if (check(TokenType.VAR) || check(TokenType.FUNC)) {
        instructions.add(parseDeclaration());
      } else if (match(TokenType.RETURN)) {
        ASTNode expr = parseExpression();
        instructions.add(new ReturnStatement(expr));
        consume(TokenType.SEMICOLON, "expecting ;");
        return new BlockNode(instructions);
      } else {
        instructions.add(parseStatement());
      }
    }
    return new BlockNode(instructions);
  }

  private ASTNode parseStatement() {
    if (match(TokenType.IF)) {
      return parseIfStatement();
    }

    throw new RuntimeException("not implemented, found: " + peek());
  }

  private ASTNode parseIfStatement() {
    consume(TokenType.LPAREN, "expecting (");
    ASTNode condition = parseExpression();
    consume(TokenType.RPAREN, "expecting )");
    consume(TokenType.LBRACE, "expecting {");
    ASTNode thenBranch = parseBlock();
    consume(TokenType.RBRACE, "expecting }");
    ASTNode elseBranch = new BlockNode(new ArrayList<>());
    if (match(TokenType.ELSE)) {
      consume(TokenType.LBRACE, "expecting {");
      elseBranch = parseBlock();
      consume(TokenType.RBRACE, "expecting }");
    }
    return new IfNode(condition, thenBranch, elseBranch);
  }

  private ASTNode parseDeclaration() {
    if (match(TokenType.VAR)) {
      return parseVarDeclaration();
    } else if (match(TokenType.FUNC)) {
      return parseFunctionDeclaration();
    }
    throw new RuntimeException("not implemented");
  }

  private ASTNode parseFunctionDeclaration() {
    Token identifier = consume(TokenType.IDENTIFIER, "expecting function name");
    consume(TokenType.LPAREN, "expecting (");
    List<ASTNode> parameters = new ArrayList<>();
    while (!match(TokenType.RPAREN)) {
      Token param = consume(TokenType.IDENTIFIER, "expecting identifier");
      parameters.add(new LiteralNode(param.toString()));
      match(TokenType.COMMA);
    }
    consume(TokenType.LBRACE, "expecting {");
    ASTNode body = parseBlock();
    consume(TokenType.RBRACE, "expecting }");
    return new FunctionDeclarationNode(identifier.toString(), parameters, body);
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

    while (check(TokenType.EQ) || check(TokenType.NE) ||
        check(TokenType.LT) || check(TokenType.GT) ||
        check(TokenType.LE) || check(TokenType.GE)) {
      Token op = advance();
      ASTNode right = parseTerm();
      left = new ComparisonExpression(left, op.toString(), right);
    }

    while (check(TokenType.PLUS) || check(TokenType.MINUS)) {
      Token op = advance();
      ASTNode right = parseTerm();
      left = new BinaryExpression(left, op.toString(), right);
    }
    return left;
  }

  private ASTNode parseTerm() {
    ASTNode left = parseFactor();
    if (check(TokenType.MULT) || check(TokenType.DIV) || check(TokenType.MOD)) {
      Token op = advance();
      ASTNode right = parseFactor();
      return new BinaryExpression(left, op.toString(), right);
    }
    return left;
  }

  private ASTNode parseFactor() {
    if (check(TokenType.NUMBER) || check(TokenType.STRING) || check(TokenType.IDENTIFIER)) {
      Token tok = advance();
      return new LiteralNode(tok.toString());
    } else if (match(TokenType.LPAREN)) {
      ASTNode expr = parseExpression();
      consume(TokenType.RPAREN, "expecting )");
      return expr;
    } else {
      throw new RuntimeException("[Error] unexpected token: " + peek());
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
    throw error(peek(), "[Error] " + message);
  }

  private boolean check(TokenType type) {
    if (end())
      return false;
    return peek().type == type;
  }

  private boolean end() {
    return peek().type == TokenType.EOF;
  }

  private Token peek() {
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
