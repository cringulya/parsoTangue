package parser.ast;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    BinaryExpression other = (BinaryExpression) obj;
    return ((Objects.equals(left, other.left) && Objects.equals(right, other.right)) ||
        (Objects.equals(right, other.left) && Objects.equals(left, other.right)))
        && Objects.equals(operator, other.operator);
  }
}
