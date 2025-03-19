package parser.ast;

import java.util.Objects;

/**
 * IfNode
 */
public class IfNode extends ASTNode {

  public ASTNode condition;
  public ASTNode thenBranch;
  public ASTNode elseBranch;

  public IfNode(ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
    this.condition = condition;
    this.thenBranch = thenBranch;
    this.elseBranch = elseBranch;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("IF statement:");
    printIndent(indent + 1);
    System.out.println("CONDITION:");
    condition.print(indent + 2);
    printIndent(indent + 1);
    System.out.println("THEN BRANCH:");
    thenBranch.print(indent + 2);
    printIndent(indent + 1);
    System.out.println("ELSE BRANCH:");
    elseBranch.print(indent + 2);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    IfNode other = (IfNode) obj;
    return Objects.equals(condition, other.condition)
        && Objects.equals(thenBranch, other.thenBranch)
        && Objects.equals(elseBranch, other.elseBranch);
  }
}
