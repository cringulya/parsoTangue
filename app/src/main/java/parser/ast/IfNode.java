package parser.ast;

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
  }
}
