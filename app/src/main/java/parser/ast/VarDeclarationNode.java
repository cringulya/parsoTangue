package parser.ast;

import java.util.Objects;

public class VarDeclarationNode extends ASTNode {
  public final String identefier;
  public final VarType type;
  public final ASTNode expression;

  public VarDeclarationNode(String identefier, VarType type, ASTNode expression) {
    this.identefier = identefier;
    this.type = type;
    this.expression = expression;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Variable declaration (" + identefier + ")");
    expression.print(indent + 1);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    VarDeclarationNode other = (VarDeclarationNode) obj;
    return Objects.equals(identefier, other.identefier)
        && Objects.equals(type, other.type)
        && Objects.equals(expression, other.expression);
  }
}
