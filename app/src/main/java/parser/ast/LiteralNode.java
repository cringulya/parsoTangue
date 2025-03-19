package parser.ast;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    LiteralNode other = (LiteralNode) obj;
    return Objects.equals(value, other.value)
        && Objects.equals(type, other.type);
  }
}
