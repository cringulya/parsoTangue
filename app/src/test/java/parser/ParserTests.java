package parser;

import org.junit.jupiter.api.Test;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import parser.ast.ASTNode;
import parser.ast.AssignmentNode;
import parser.ast.BinaryExpression;
import parser.ast.BlockNode;
import parser.ast.FunctionCallNode;
import parser.ast.FunctionDeclarationNode;
import parser.ast.FunctionParameter;
import parser.ast.IfNode;
import parser.ast.LiteralNode;
import parser.ast.ReturnStatement;
import parser.ast.VarDeclarationNode;
import parser.ast.VarType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ParserTests {

  @Test
  void emptyString() {
    String input = "";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    assertEquals(ast, new BlockNode(new ArrayList<>()));
  }

  @Test
  void varDeclaration() {
    String input = "int a = 1;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new VarDeclarationNode("a", VarType.INTEGER,
                new LiteralNode(1, VarType.INTEGER))));
    assertEquals(ast, ans);
  }

  @Test
  void noSemicolon() {
    String input = "int a = 1";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    assertThrows(RuntimeException.class, () -> parser.parse());
  }

  @Test
  void varAssign() {
    String input = "a = 3;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a", new LiteralNode(3, VarType.INTEGER))));
    assertEquals(ast, ans);
  }

  @Test
  void functionDeclaration() {
    String input = "func af__(int a){ return 1; }";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new FunctionDeclarationNode("af__",
                Arrays.asList(new FunctionParameter("a", VarType.INTEGER)),
                new BlockNode(
                    Arrays.asList(
                        new ReturnStatement(
                            new LiteralNode(1, VarType.INTEGER)))))));
    assertEquals(ast, ans);
  }

  @Test
  void functionCall() {
    String input = "af__(0);";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new FunctionCallNode("af__",
                Arrays.asList(new LiteralNode(0, VarType.INTEGER)))));
    assertEquals(ast, ans);
  }

  @Test
  void ifStatement() {
    String input = "if (1) {}";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new IfNode(
                new LiteralNode(1, VarType.INTEGER),
                new BlockNode(new ArrayList<>()),
                new BlockNode(new ArrayList<>()))));
    assertEquals(ast, ans);
  }

  @Test
  void ifStatementEmptyCondition() {
    String input = "if () {}";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    assertThrows(RuntimeException.class, () -> parser.parse());
  }

  @Test
  void justExpression() {
    String input = "1;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    assertThrows(RuntimeException.class, () -> parser.parse());
  }

  @Test
  void distributivity() {
    String input = "a = 1 * 2 + 3 * 4;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a",
                new BinaryExpression(
                    new BinaryExpression(
                        new LiteralNode(1, VarType.INTEGER),
                        "*",
                        new LiteralNode(2, VarType.INTEGER)),
                    "+",
                    new BinaryExpression(
                        new LiteralNode(3, VarType.INTEGER),
                        "*",
                        new LiteralNode(4, VarType.INTEGER))))));
    assertEquals(ast, ans);
  }

  @Test
  void modPriority() {
    String input = "a = 1 % 2 + 3 % 4;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a",
                new BinaryExpression(
                    new BinaryExpression(
                        new LiteralNode(1, VarType.INTEGER),
                        "%",
                        new LiteralNode(2, VarType.INTEGER)),
                    "+",
                    new BinaryExpression(
                        new LiteralNode(3, VarType.INTEGER),
                        "%",
                        new LiteralNode(4, VarType.INTEGER))))));
    assertEquals(ast, ans);
  }

  @Test
  void modPriority2() {
    String input = "a = 1 % 2 * 3;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a",
                new BinaryExpression(
                    new BinaryExpression(
                        new LiteralNode(1, VarType.INTEGER),
                        "%",
                        new LiteralNode(2, VarType.INTEGER)),
                    "*",
                    new LiteralNode(3, VarType.INTEGER)))));
    assertEquals(ast, ans);
  }

  @Test
  void expression3() {
    String input = "a = 1 < 2 > 3;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a",
                new BinaryExpression(
                    new BinaryExpression(
                        new LiteralNode(1, VarType.INTEGER),
                        "<",
                        new LiteralNode(2, VarType.INTEGER)),
                    ">",
                    new LiteralNode(3, VarType.INTEGER)))));
    assertEquals(ast, ans);
  }

  @Test
  void expression4() {
    String input = "a = 1 < 2 * 3;";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    ASTNode ans = new BlockNode(
        Arrays.asList(
            new AssignmentNode("a",
                new BinaryExpression(
                    new BinaryExpression(
                        new LiteralNode(2, VarType.INTEGER),
                        "*",
                        new LiteralNode(3, VarType.INTEGER)),
                    "<",
                    new LiteralNode(1, VarType.INTEGER)))));
    assertEquals(ast, ans);
  }
}
