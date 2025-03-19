package parser.ast;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    ReturnStatement other = (ReturnStatement) obj;
    return Objects.equals(expression, other.expression);
  }
}
