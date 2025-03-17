package parser.ast;

/**
 * ComparisonExpression
 */
public class ComparisonExpression extends ASTNode {
  ASTNode left;
  String operator;
  ASTNode right;

  public ComparisonExpression(ASTNode left, String operator, ASTNode right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("BinaryExpr (" + operator + ")");
    left.print(indent + 1);
    right.print(indent + 1);
  }
}
