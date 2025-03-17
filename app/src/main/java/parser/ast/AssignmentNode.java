package parser.ast;

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
}
