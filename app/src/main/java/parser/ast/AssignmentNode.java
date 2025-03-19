package parser.ast;

import java.util.Objects;

public class AssignmentNode extends ASTNode {
  public final String identefier;
  public final ASTNode expression;

  public AssignmentNode(String identefier, ASTNode expression) {
    this.identefier = identefier;
    this.expression = expression;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("AssignmentNode (" + identefier + ")");
    expression.print(indent + 1);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    AssignmentNode other = (AssignmentNode) obj;
    return Objects.equals(identefier, other.identefier) && Objects.equals(expression, other.expression);
  }
}
