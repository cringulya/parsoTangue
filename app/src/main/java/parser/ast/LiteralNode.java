package parser.ast;

public class LiteralNode extends ASTNode {
  public final Object value;
  public final VarType type;

  public LiteralNode(Object value, VarType type) {
    this.value = value;
    this.type = type;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Literal (" + type + ' ' + value + ")");
  }
}
