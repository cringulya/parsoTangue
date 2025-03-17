package parser.ast;

public class LiteralNode extends ASTNode {
  public final Object value;

  public LiteralNode(Object value) {
    this.value = value;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Literal (" + value + ")");
  }
}
