package parser;

import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import lexer.TokenType;
import parser.ast.ASTNode;
import parser.ast.AssignmentNode;
import parser.ast.BinaryExpression;
import parser.ast.BlockNode;
import parser.ast.ComparisonExpression;
import parser.ast.FunctionCallNode;
import parser.ast.FunctionDeclarationNode;
import parser.ast.FunctionParameter;
import parser.ast.IfNode;
import parser.ast.LiteralNode;
import parser.ast.ReturnStatement;
import parser.ast.VarDeclarationNode;
import parser.ast.VarType;

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
      if (check(TokenType.INT) || check(TokenType.STR) || check(TokenType.FUNC)) {
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
    } else if (check(TokenType.IDENTIFIER)) {
      int init_pos = this.pos;
      try {
        ASTNode call = parseFunctionCall();
        consume(TokenType.SEMICOLON, "expecting ;");
        return call;
      } catch (Exception e1) {
        try {
          this.pos = init_pos;
          ASTNode assign = parseAssignStatement();
          consume(TokenType.SEMICOLON, "expecting ;");
          return assign;
        } catch (Exception e2) {
          throw error(peek(), "couldn't parse statement");
        }
      }
    }

    throw error(peek(), "not implemented");
  }

  private ASTNode parseAssignStatement() {
    Token id = consume(TokenType.IDENTIFIER, "expecting identifier");
    consume(TokenType.ASSIGN, "expecting =");
    ASTNode expr = parseExpression();
    return new AssignmentNode(id.toString(), expr);
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
    if (check(TokenType.INT) || check(TokenType.STR)) {
      return parseVarDeclaration();
    } else if (match(TokenType.FUNC)) {
      return parseFunctionDeclaration();
    }
    throw error(peek(), "not implemented");
  }

  private ASTNode parseFunctionDeclaration() {
    Token identifier = consume(TokenType.IDENTIFIER, "expecting function name");
    consume(TokenType.LPAREN, "expecting (");
    List<ASTNode> parameters = new ArrayList<>();
    while (!match(TokenType.RPAREN)) {
      if (match(TokenType.STRING)) {
        Token name = consume(TokenType.IDENTIFIER, "expecting parameter identifier");
        parameters.add(new FunctionParameter(name.lexeme, VarType.STRING));
      } else if (match(TokenType.INT)) {
        Token name = consume(TokenType.IDENTIFIER, "expecting parameter identifier");
        parameters.add(new FunctionParameter(name.lexeme, VarType.INTEGER));
      }
      match(TokenType.COMMA);
    }
    consume(TokenType.LBRACE, "expecting {");
    ASTNode body = parseBlock();
    consume(TokenType.RBRACE, "expecting }");
    return new FunctionDeclarationNode(identifier.lexeme, parameters, body);
  }

  private ASTNode parseFunctionCall() {
    Token id = consume(TokenType.IDENTIFIER, "expecting function identifier");
    consume(TokenType.LPAREN, "expecting (");

    List<ASTNode> arguments = new ArrayList<>();
    while (!match(TokenType.RPAREN)) {
      ASTNode expr = parseExpression();
      arguments.add(expr);
      match(TokenType.COMMA);
    }

    return new FunctionCallNode(id.lexeme, arguments);

  }

  private ASTNode parseVarDeclaration() {
    VarType type;
    if (match(TokenType.INT)) {
      type = VarType.INTEGER;
    } else if (match(TokenType.STR)) {
      type = VarType.STRING;
    } else {
      throw error(peek(), "expecting a type");
    }
    Token identifier = consume(TokenType.IDENTIFIER, "expecting variable name");
    consume(TokenType.ASSIGN, "expecting =");
    ASTNode expression = parseExpression();
    consume(TokenType.SEMICOLON, "expecting ;");
    return new VarDeclarationNode(identifier.lexeme, type, expression);
  }

  private ASTNode parseExpression() {
    ASTNode left = parseTerm();

    while (check(TokenType.EQ) || check(TokenType.NE) ||
        check(TokenType.LT) || check(TokenType.GT) ||
        check(TokenType.LE) || check(TokenType.GE)) {
      Token op = advance();
      ASTNode right = parseTerm();
      left = new BinaryExpression(left, op.toString(), right);
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
      return parseLiteral();
    } else if (match(TokenType.LPAREN)) {
      ASTNode expr = parseExpression();
      consume(TokenType.RPAREN, "expecting )");
      return expr;
    } else {
      throw error(peek(), "unexpected token");
    }
  }

  private ASTNode parseLiteral() {
    if (check(TokenType.NUMBER)) {
      Token tok = advance();
      return new LiteralNode(
          Integer.parseInt(tok.lexeme),
          VarType.INTEGER);
    } else if (check(TokenType.STRING)) {
      Token tok = advance();
      return new LiteralNode(
          tok.lexeme,
          VarType.STRING);
    } else if (check(TokenType.IDENTIFIER)) {
      Token tok = advance();
      return new LiteralNode(
          tok.lexeme,
          VarType.REFERENCE);
    }
    throw error(peek(), "unexpected token");
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
    throw error(peek(), message);
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
    return new RuntimeException("[Parser Error] line:" + token.line + " at token \"" + token.lexeme + "\": " + message);
  }

}
