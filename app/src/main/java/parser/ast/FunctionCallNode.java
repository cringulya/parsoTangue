package parser.ast;

import java.util.List;

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
    System.out.println("Function call:");
    printIndent(indent + 1);
    System.out.println("Arguments:");
    for (ASTNode param : arguments) {
      param.print(indent + 2);
    }
  }
}
