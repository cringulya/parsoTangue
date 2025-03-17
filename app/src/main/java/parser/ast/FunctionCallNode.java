package parser.ast;

import java.util.List;

/**
 * FunctionCallNode
 */
public class FunctionCallNode extends ASTNode {
  String name;
  List<ASTNode> arguments;

  public FunctionCallNode(String name, List<ASTNode> arguments) {
    this.name = name;
    this.arguments = arguments;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("FunctionCallNode (" + name + ")");
  }
}
