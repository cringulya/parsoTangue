package parser.ast;

import java.util.List;


/**
 * BlockNode
 */
public class BlockNode extends ASTNode {
  public List<ASTNode> instructions;

  public BlockNode(List<ASTNode> statements) {
    this.instructions = statements;
  }

  @Override
  public void print(int indent) {
    printIndent(indent);
    System.out.println("Block node:");
    for (ASTNode node : instructions) {
      node.print(indent + 1);
    }
  }
}
