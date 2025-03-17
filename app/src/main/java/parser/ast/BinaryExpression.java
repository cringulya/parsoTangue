package parser.ast;

/**
 * BinaryExpression
 */
public class BinaryExpression extends ASTNode {
  ASTNode left;
  String operator;
  ASTNode right;

  public BinaryExpression(ASTNode left, String operator, ASTNode right) {
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
