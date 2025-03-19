package parser;

import org.junit.jupiter.api.Test;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import parser.ast.ASTNode;
import parser.ast.BlockNode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ParserTests {

  @Test
  void emptyString() {
    String input = "";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    Parser parser = new Parser(tokens);
    ASTNode ast = parser.parse();
    assertEquals(((BlockNode)ast).instructions, new ArrayList<>());
  }
}
