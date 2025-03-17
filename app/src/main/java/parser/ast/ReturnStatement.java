package parser.ast;

/**
 * ReturnStatement
 */
public class ReturnStatement extends ASTNode {
  public final ASTNode expression;

  public ReturnStatement(ASTNode expression) {
    this.expression = expression;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("RETURN STATEMENT:");
    expression.print(indent + 1);
  }
}
