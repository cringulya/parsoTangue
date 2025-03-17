package parser.ast;

import java.util.List;


/**
 * BlockNode
 */
public class BlockNode extends ASTNode {

  List<ASTNode> statements;

  public BlockNode(List<ASTNode> statements) {
    this.statements = statements;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Block node:");
    for (ASTNode node : statements) {
      node.print(indent + 1);
    }
  }
}
