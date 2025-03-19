package parser.ast;

import java.util.List;
import java.util.Objects;

public class FunctionCallNode extends ASTNode {
  public final String identefier;
  public final List<ASTNode> arguments;

  public FunctionCallNode(String identefier, List<ASTNode> arguments) {
    this.identefier = identefier;
    this.arguments = arguments;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Function call (" + identefier + ")");
    printIndent(indent + 1);
    System.out.println("Arguments:");
    for (ASTNode param : arguments) {
      param.print(indent + 2);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    FunctionCallNode other = (FunctionCallNode) obj;
    return Objects.equals(identefier, other.identefier)
        && Objects.equals(arguments, other.arguments);
  }
}
